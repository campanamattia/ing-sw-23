package Server.Controller;

import Enumeration.TurnPhase;
import Exception.Player.PlayerNotFoundException;
import Exception.PlayerException;
import Exception.ChatException;
import Exception.BoardException;
import Exception.GamePhase.EndGameException;
import Exception.GamePhaseException;
import Exception.Player.NotYourTurnException;
import Interface.Scout.BoardScout;
import Interface.Scout.ChatScout;
import Interface.Scout.CommonGoalScout;
import Interface.Scout.PlayerScout;
import Interface.Server.GameCommand;
import Server.Controller.Phase.EndedMatch;
import Server.Controller.Phase.LastRoundState;
import Server.Controller.Phase.NormalState;
import Server.Controller.Phase.PhaseController;
import Server.Model.*;
import Server.Network.Client.ClientHandler;
import Server.ServerApp;
import Utils.Coordinates;
import Utils.Rank;

import java.io.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;

public class GameController implements GameCommand, Serializable {
    private final UUID uuid;
    private GameModel gameModel;
    private final HashMap<String, ClientHandler> players;


    private PhaseController phaseController;

    private TurnPhase turnPhase;
    private final CurrentPlayer currentPlayer;

    public GameController(HashMap<String, ClientHandler> players){
        this.uuid = UUID.randomUUID();
        this.players = players;
        List<String> playersID = new ArrayList<>(players.keySet());
        try {
            this.gameModel = new GameModel(this.uuid, playersID);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.toString());
            for(ClientHandler client : players.values()) {
                try {
                    client.remoteView().outcomeException(e);
                } catch (RemoteException ex) {
                    ServerApp.logger.severe(ex.toString());
                }
            }
        }
        this.turnPhase = TurnPhase.PICKING;
        this.currentPlayer = new CurrentPlayer(this.gameModel.getCurrentPlayer());
        this.phaseController = new NormalState(this.gameModel.getCurrentPlayer(), this.gameModel.getPlayers());
    }

    /**
     This method ends the current turn, checks for common goals, advances to the next player, and updates the gameModel status.
     If the gameModel has entered its last round, it changes the gameModel phase accordingly.
     If the gameModel has ended, it sets the leaderboard and gameModel phase to ended.
     */
    public void endTurn() throws IOException {
        phaseController.checkCommonGoals(this.gameModel.getCommonGoals());
        do {
            try {
                phaseController.nextPlayer();
                this.gameModel.setCurrentPlayer(this.phaseController.getCurrentPlayer());
                this.currentPlayer.reset(this.gameModel.getCurrentPlayer());
                break;
            } catch (GamePhaseException e) {
                if (e instanceof EndGameException) {
                    List<Rank> leaderboard = EndedMatch.doRank(this.gameModel.getPlayers());
                    for (ClientHandler client : players.values()) {
                        try {
                            client.remoteView().endGame(leaderboard);
                        } catch (RemoteException ex) {
                            ServerApp.logger.severe(ex.toString());
                        }
                    }
                    break;
                } else {
                    this.phaseController = new LastRoundState(this.phaseController.getCurrentPlayer(), this.phaseController.getPlayers());
                    this.gameModel.setPhase(phaseController.getPhase());
                }
            } finally {
                this.gameModel.updateStatus();
            }
        }while(true);
         for (ClientHandler client : players.values()) {
             try {
                 client.remoteView().newTurn(this.gameModel.getCurrentPlayer().getPlayerID());
             } catch (RemoteException ex) {
                 ServerApp.logger.severe(ex.toString());
             }
         }
    }

    /**
     Returns the UUID associated with this GameController.
     @return the UUID associated with this GameController
     */
    @SuppressWarnings("unused")
    public UUID getUuid() {
        return uuid;
    }
    /**
     Returns the GameModel associated with this GameController.
     @return the GameModel associated with this GameController
     */
    @SuppressWarnings("unused")
    public GameModel getGameModel() {
        return gameModel;
    }


    @Override
    public synchronized void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        try {
            if(ableTo(playerID) == TurnPhase.PICKING) {
                try {
                    currentPlayer.setTiles(this.gameModel.selectedTiles(coordinates));
                    this.turnPhase = TurnPhase.INSERTING;
                    this.players.get(playerID).remoteView().outcomeSelectTiles(currentPlayer.getTiles());
                } catch (BoardException e) {
                    players.get(playerID).remoteView().outcomeException(e);
                }
            }
        } catch (NotYourTurnException e) {
            players.get(playerID).remoteView().outcomeException(e);
        }
    }

    @Override
    public synchronized void insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException {
        try {
            if(ableTo(playerID) == TurnPhase.PICKING) {
                try {
                    this.gameModel.insertTiles(sort, currentPlayer.getTiles(), column);
                    this.players.get(playerID).remoteView().outcomeInsertTiles(true);
                    this.turnPhase = TurnPhase.PICKING;
                    endTurn();
                } catch (PlayerException e) {
                    this.players.get(playerID).remoteView().outcomeException(e);
                } catch (IOException e) {
                    ServerApp.logger.severe(e.toString());
                }
            }
        } catch (NotYourTurnException e) {
            this.players.get(playerID).remoteView().outcomeException(e);
        }
    }

    @Override
    public  synchronized void writeChat(String playerID, String message) throws RemoteException {
        try {
            this.gameModel.writeChat(playerID, message);
        } catch (ChatException e) {
            this.players.get(playerID).remoteView().outcomeException(e);
        }
    }


    @Override
    public synchronized void addSubscriber(Object object) throws RemoteException {
        this.gameModel.addBoardScout((BoardScout) object);
        this.gameModel.addChatScout((ChatScout) object);
        this.gameModel.addPlayerScout((PlayerScout) object);
        this.gameModel.addCommonGoalScout((CommonGoalScout) object);
    }

    public void reload (String playerID, ClientHandler client) throws RemoteException {
        this.players.put(playerID, client);
        try {
            this.gameModel.getPlayer(playerID).setStatus(true);
        } catch (PlayerNotFoundException e) {
            ServerApp.logger.severe(e.toString());
            client.remoteView().outcomeException(e);
        }
        for(ClientHandler clientHandler : players.values()) {
            try {
                clientHandler.remoteView().reloadPlayer(playerID);
            } catch (RemoteException e) {
                ServerApp.logger.severe(e.toString());
            }
        }
    }

    public synchronized void logOut(String playerID) throws RemoteException{
        try {
            this.gameModel.getPlayer(playerID).setStatus(false);
            this.players.remove(playerID);
            if(this.gameModel.getCurrentPlayer().getPlayerID().equals(playerID) && this.turnPhase == TurnPhase.INSERTING)
                this.gameModel.completeTurn(playerID);
            else {
                for(ClientHandler client : players.values()) {
                    try {
                        client.remoteView().crashedPlayer(playerID);
                    } catch (RemoteException e) {
                        ServerApp.logger.severe(e.toString());
                    }
                }
            }
        } catch (PlayerException e) {
            ServerApp.logger.severe(e+" for logout");
        }
    }

    private TurnPhase ableTo(String playerID) throws NotYourTurnException {
        if(!playerID.equals(this.currentPlayer.getCurrentPlayer().getPlayerID()))
            throw new NotYourTurnException(this.gameModel.getCurrentPlayer().getPlayerID());
        else return this.turnPhase;
    }
}