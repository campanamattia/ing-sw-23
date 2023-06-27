package Server.Controller;

import Enumeration.TurnPhase;
import Enumeration.GameWarning;
import Exception.Board.CantRefillBoardException;
import Exception.Board.NoValidMoveException;
import Exception.Board.NullTileException;
import Exception.CommonGoal.NullPlayerException;
import Exception.GamePhase.EndGameException;
import Exception.GamePhase.EndingStateException;
import Exception.Player.PlayerNotFoundException;
import Exception.PlayerException;
import Exception.ChatException;
import Exception.GamePhaseException;
import Exception.Player.NotYourTurnException;
import Interface.Scout;
import Interface.Server.GameCommand;
import Server.Controller.Phase.EndedMatch;
import Server.Controller.Phase.LastRoundState;
import Server.Controller.Phase.NormalState;
import Server.Controller.Phase.PhaseController;
import Server.Model.*;
import Server.Model.LivingRoom.CommonGoal.CommonGoal;
import Server.Model.Player.Player;
import Server.Network.Client.ClientHandler;
import Utils.Coordinates;
import Utils.MockObjects.MockFactory;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.stream.Collectors;


import static Server.ServerApp.*;

/**
 * The GameController class represents the controller for a game.
 * It manages the game model, players, phases, and turns progression.
 * This class implements the GameCommand interface and is Serializable.
 */
public class GameController extends UnicastRemoteObject implements GameCommand, Serializable {
    /**
     * The gameID attribute represents the ID of the game.
     */
    private final String gameID;
    /**
     * The GameModel class represents the model for a game. It contains the gameModel board, players, and common goals.
     */
    private GameModel gameModel;
    /**
     * The players HashMap contains the players of the gameModel.
     */
    private final HashMap<String, ClientHandler> players;
    /**
     * The phaseController attribute represents the current phase of the gameModel.
     */
    private PhaseController phaseController;
    /**
     * The turnPhase attribute represents the current turn phase of the gameModel.
     */
    private TurnPhase turnPhase;
    /**
     * The currentPlayer attribute represents the current player of the gameModel.
     */
    private CurrentPlayer currentPlayer;
    /**
     * The wait attribute represents the timer used to wait for players to rejoin the game
     */
    private Timer wait;

    private boolean isWaiting = false;


    /**
     * Constructs a new GameController instance with the specified game model and players.
     *
     * @param lobbyID the lobby ID that the gameController is associated with.
     * @param players A HashMap of players participating in the game, where the key is the player ID and the value is the corresponding ClientHandler.
     */
    public GameController(String lobbyID, HashMap<String, ClientHandler> players) throws RemoteException {
        super();
        this.gameID = lobbyID;
        this.players = players;
        try {
            List<String> playerIDs = new ArrayList<>(players.keySet());
            Collections.shuffle(playerIDs);
            this.gameModel = new GameModel(lobbyID, playerIDs);
            this.currentPlayer = new CurrentPlayer(this.gameModel.getCurrentPlayer());
            this.turnPhase = TurnPhase.PICKING;
            this.phaseController = new NormalState(this.gameModel.getCurrentPlayer(), this.gameModel.getPlayers());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
            sendException(e);
            lobby.endGame(this);
        }
    }

    private void endTurn() throws IOException {
        checkCommonGoals(this.gameModel.getCommonGoals());
        this.gameModel.getTalent().onEvent(MockFactory.getMock(this.currentPlayer.getCurrentPlayer()));
        try {
            this.gameModel.checkRefill();
        } catch (CantRefillBoardException e) {
            lobby.endGame(this);
            EndedMatch.doRank(activePlayers(), this.gameModel.getPlayers());
            this.turnPhase = TurnPhase.ENDED;
        }

        if (activePlayers().size() == 1) {
            isWaiting = true;
            this.turnPhase = TurnPhase.WAITING;
        }
    }

    private void nextPlayer() throws EndGameException {
        do {
            try {
                phaseController.nextPlayer();
            } catch (GamePhaseException e) {
                if (e instanceof EndingStateException) {
                    this.phaseController = new LastRoundState(this.phaseController.getCurrentPlayer(), this.phaseController.getPlayers());
                    sendMessage(GameWarning.LAST_ROUND);
                    continue;
                } else throw (EndGameException) e;
            }
            this.gameModel.setCurrentPlayer(this.phaseController.getCurrentPlayer());
            this.currentPlayer.reset(this.gameModel.getCurrentPlayer());
            break;
        } while (true);
    }

    private void newTurn() {
        this.turnPhase = TurnPhase.PICKING;
        try {
            nextPlayer();
        } catch (EndGameException e) {
            this.phaseController = null;
            EndedMatch.doRank(this.players.values(), this.gameModel.getPlayers());
            lobby.endGame(this);
            this.turnPhase = TurnPhase.ENDED;
            return;
        }
        for (ClientHandler client : activePlayers()) {
            executorService.execute(() -> {
                try {
                    client.remoteView().newTurn(this.gameModel.getCurrentPlayer().getPlayerID());
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
        }
    }

    private void checkCommonGoals(List<CommonGoal> commonGoals) {
        for (CommonGoal common : commonGoals)
            if (!common.getAccomplished().contains(this.currentPlayer.getCurrentPlayer().getPlayerID())) try {
                common.check(this.currentPlayer.getCurrentPlayer());
                this.gameModel.getTalent().onEvent(MockFactory.getMock(common));
            } catch (NullPlayerException e) {
                logger.severe(e.getMessage());
            }
    }

    /**
     * Returns the GameModel associated with this GameController.
     *
     * @return the GameModel associated with this GameController
     */
    public GameModel getGameModel() {
        return gameModel;
    }


    /**
     * Allows a player to select tiles during their turn. The selected tiles will be used for inserting them on the game board.
     *
     * @param playerID    The ID of the player making the selection.
     * @param coordinates The list of coordinates representing the tiles to be selected.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        try {
            if (ableTo(playerID) != TurnPhase.PICKING) {
                sendException(new RuntimeException(this.turnPhase.toString()), this.players.get(playerID));
                return;
            }
        } catch (NotYourTurnException e) {
            sendException(e, this.players.get(playerID));
            return;
        }

        try {
            currentPlayer.setTiles(this.gameModel.selectTiles(coordinates));
            this.turnPhase = TurnPhase.INSERTING;
            executorService.execute(() -> {
                try {
                    this.players.get(playerID).remoteView().outcomeSelectTiles(currentPlayer.getTiles());
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
        } catch (NoValidMoveException | NullTileException e) {
            sendException(e, this.players.get(playerID));
        }
    }

    /**
     * Inserts the selected tiles onto the game board at the specified column.
     *
     * @param playerID The ID of the player performing the tile insertion.
     * @param sort     The list of indexes representing the sorting order of the tiles to be inserted.
     * @param column   The column where the tiles will be inserted.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public void insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException {
        try {
            if (ableTo(playerID) != TurnPhase.INSERTING) {
                sendException(new RuntimeException(this.turnPhase.toString()), this.players.get(playerID));
                return;
            }
        } catch (NotYourTurnException e) {
            sendException(e, this.players.get(playerID));
            return;
        }

        try {
            this.gameModel.insertTiles(sort, currentPlayer.getTiles(), column);
            this.players.get(playerID).remoteView().outcomeInsertTiles(true);
            endTurn();
        } catch (PlayerException e) {
            sendException(e, this.players.get(playerID));
            return;
        } catch (IOException e) {
            logger.severe(e.toString());
            return;
        }

        if (this.turnPhase == TurnPhase.ENDED)
            return;
        if (isWaiting) {
            sendException(new RuntimeException("Waiting for other players to reconnect"), this.players.get(playerID));
            return;
        }
        newTurn();
    }

    /**
     * Writes a chat message from a player to another player or the entire game.
     * If the recipient is null, the message is sent to all players in the game.
     *
     * @param playerID The ID of the player sending the chat message.
     * @param message  The content of the chat message.
     * @param to       The recipient of the chat message. If null, the message is sent to all players in the game.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public synchronized void writeChat(String playerID, String message, String to) throws RemoteException {
        if (this.turnPhase == TurnPhase.ENDED) {
            sendException(new RuntimeException("The game has ended"), this.players.get(playerID));
            return;
        }
        try {
            this.gameModel.writeChat(playerID, message, to);
        } catch (ChatException e) {
            this.players.get(playerID).remoteView().outcomeException(e);
        }
    }


    /**
     * Adds a subscriber (Scout) to the game, allowing them to receive updates on the game state.
     *
     * @param scout The Scout object to be added as a subscriber.
     * @throws RemoteException If a remote communication error occurs.
     */
    @SuppressWarnings("rawtypes")
    @Override
    public synchronized void addScout(String playerID, Scout scout) throws RemoteException {
        Talent talent = gameModel.getTalent();
        talent.addScout(playerID, scout);
    }

    /**
     * Rejoin a player in the game after disconnecting.
     *
     * @param playerID The ID of the player to be reloaded.
     * @param client   The ClientHandler object associated with the player.
     */
    public void rejoin(String playerID, ClientHandler client) {
        try {
            Player player = this.gameModel.getPlayer(playerID);
            player.setStatus(true);
        } catch (PlayerNotFoundException e) {
            logger.severe(e.toString());
            sendException(e, client);
            return;
        }

        for (ClientHandler clientHandler : activePlayers()){
            executorService.execute(() -> {
                try {
                    clientHandler.remoteView().reloadPlayer(playerID);
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
        }
        sendMessage(GameWarning.STOP_TIMER);

        this.players.put(playerID, client);


        if (wait != null) {
            wait.cancel();
            wait = null;
        }

        if (isWaiting) {
            isWaiting = false;
            newTurn();
        }
    }


    /**
     * Logs out a player from the game and updates their status.
     *
     * @param playerID The ID of the player to be logged out.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void logOut(String playerID) throws IOException {
        // Set the player's status to logged out
        try {
            Player player = this.gameModel.getPlayer(playerID);
            this.gameModel.getTalent().removeScout(playerID);
            player.setStatus(false);
        } catch (PlayerNotFoundException e) {
            logger.severe(e.getMessage());
            return;
        }

        if (currentPlayer.getCurrentPlayer().getPlayerID().equals(playerID) && this.turnPhase == TurnPhase.INSERTING) {
            this.gameModel.completeTurn(this.currentPlayer.getTiles());
            endTurn();
        }

        // Notify other active players about the player being logged out
        this.players.put(playerID, null);
        int numActivePlayers = activePlayers().size();

        for (ClientHandler client : activePlayers()) {
            try {
                client.remoteView().crashedPlayer(playerID);
            } catch (RemoteException e) {
                logger.severe(e.getMessage());
            }
        }


        // No active players remaining, end the game
        if (numActivePlayers == 0) {
            this.phaseController = null;
            lobby.endGame(this);
            if (wait != null) wait.cancel();
            return;
        }

        // If the logged-out player was the current player, complete their turn and end the turn
        if (currentPlayer.getCurrentPlayer().getPlayerID().equals(playerID)) {
            newTurn();
        }

        // Only one active player remaining, start a timer to declare them the winner
        if (numActivePlayers == 1) {
            sendMessage(GameWarning.START_TIMER);
            this.wait = new Timer();
            this.wait.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendMessage(GameWarning.WON);
                    lobby.endGame(GameController.this);
                }
            }, 60000); // 60 seconds
        }
    }


    private TurnPhase ableTo(String playerID) throws NotYourTurnException {
        if (this.turnPhase == TurnPhase.ENDED)
            return null;

        if (playerID.equals(this.currentPlayer.getCurrentPlayer().getPlayerID()))
            return this.turnPhase;
        throw new NotYourTurnException(this.gameModel.getCurrentPlayer().getPlayerID());
    }

    /**
     * Returns the ID of the game.
     *
     * @return The ID of the game.
     */
    public String getGameID() {
        return this.gameID;
    }

    /**
     * Returns the map of players in the game.
     *
     * @return The map of players in the game.
     */
    public HashMap<String, ClientHandler> getPlayers() {
        return players;
    }

    /**
     * Return the list of players in the game that are still active.
     *
     * @return The list of players in the game that are still active.
     */
    public List<ClientHandler> activePlayers() {
        return this.players.values().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private void sendException(Exception e){
        for (ClientHandler client : activePlayers()) {
            executorService.execute(() -> {
                try {
                    client.remoteView().outcomeException(e);
                } catch (RemoteException ex) {
                    logger.severe(ex.getMessage());
                }
            });
        }
    }

    private void sendException(Exception e, ClientHandler client) {
        executorService.execute(() -> {
            try {
                client.remoteView().outcomeException(e);
            } catch (RemoteException ex) {
                logger.severe(ex.getMessage());
            }
        });
    }

    private void sendMessage(GameWarning warning){
        for (ClientHandler client : activePlayers())
            executorService.execute(()->{
                try{
                    client.remoteView().outcomeMessage(warning);
                } catch (RemoteException e){
                    logger.severe(e.getMessage());
                }
            });
    }
}