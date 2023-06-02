package Server.Controller;

import Enumeration.TurnPhase;
import Exception.GamePhase.EndingStateException;
import Exception.Player.PlayerNotFoundException;
import Exception.PlayerException;
import Exception.ChatException;
import Exception.BoardException;
import Exception.GamePhaseException;
import Exception.Player.NotYourTurnException;
import Interface.Scout;
import Interface.Server.GameCommand;
import Server.Controller.Phase.EndedMatch;
import Server.Controller.Phase.LastRoundState;
import Server.Controller.Phase.NormalState;
import Server.Controller.Phase.PhaseController;
import Server.Model.*;
import Server.Network.Client.ClientHandler;
import Utils.Coordinates;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;

import static Server.ServerApp.executorService;
import static Server.ServerApp.logger;

/**
 * The GameController class represents the controller for a game. It manages the game model, players, phases, and turn progression.
 * This class implements the GameCommand interface and is Serializable.
 */
public class GameController extends UnicastRemoteObject implements GameCommand, Serializable {
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
    private final CurrentPlayer currentPlayer;

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
            this.gameModel = new GameModel(lobbyID, new ArrayList<>(players.keySet()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
            for (ClientHandler client : players.values()) {
                try {
                    client.remoteView().outcomeException(e);
                } catch (RemoteException ex) {
                    logger.severe(ex.toString());
                }
            }
        }
        this.turnPhase = TurnPhase.PICKING;
        this.currentPlayer = new CurrentPlayer(this.gameModel.getCurrentPlayer());
        this.phaseController = new NormalState(this.gameModel.getCurrentPlayer(), this.gameModel.getPlayers());
    }

    /**
     * This method ends the current turn, checks for common goals, advances to the next player, and updates the gameModel status.
     * If the gameModel has entered its last round, it changes the gameModel phase accordingly.
     * If the gameModel has ended, it sets the leaderboard and gameModel phase to ended.
     */
    public void endTurn() throws IOException {
        phaseController.checkCommonGoals(this.gameModel.getCommonGoals());
        do {
            try {
                phaseController.nextPlayer();
            } catch (GamePhaseException e) {
                if (e instanceof EndingStateException) {
                    this.phaseController = new LastRoundState(this.phaseController.getCurrentPlayer(), this.phaseController.getPlayers());
                    continue;
                } else {
                    EndedMatch.doRank(this.players.values(), this.gameModel.getPlayers());
                    return;
                }
            }
            this.gameModel.setCurrentPlayer(this.phaseController.getCurrentPlayer());
            this.currentPlayer.reset(this.gameModel.getCurrentPlayer());
            break;
        } while (true);
        for (ClientHandler client : players.values()) {
            executorService.execute(() -> {
                try {
                    client.remoteView().newTurn(this.gameModel.getCurrentPlayer().getPlayerID());
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
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
    public synchronized void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        try {
            if (ableTo(playerID) == TurnPhase.PICKING) {
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
                } catch (BoardException e) {
                    executorService.execute(() -> {
                        try {
                            players.get(playerID).remoteView().outcomeException(e);
                        } catch (RemoteException ex) {
                            logger.severe(ex.getMessage());
                        }
                    });
                }
            }
        } catch (NotYourTurnException e) {
            executorService.execute(() -> {
                try {
                    players.get(playerID).remoteView().outcomeException(e);
                } catch (RemoteException ex) {
                    logger.severe(ex.getMessage());
                }
            });
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
    public synchronized void insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException {
        logger.info("Player " + playerID + " is inserting tiles");
        try {
            if (ableTo(playerID) == TurnPhase.INSERTING) {
                try {
                    this.gameModel.insertTiles(sort, currentPlayer.getTiles(), column);
                    this.players.get(playerID).remoteView().outcomeInsertTiles(true);
                    this.turnPhase = TurnPhase.PICKING;
                    endTurn();
                } catch (PlayerException e) {
                    this.players.get(playerID).remoteView().outcomeException(e);
                } catch (IOException e) {
                    logger.severe(e.toString());
                }
            }
        } catch (NotYourTurnException e) {
            this.players.get(playerID).remoteView().outcomeException(e);
        }
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
    @Override
    public synchronized void addSubscriber(Scout scout) throws RemoteException {
        this.gameModel.addBoardScout(scout);
        this.gameModel.addChatScout(scout);
        this.gameModel.addPlayerScout(scout);
        this.gameModel.addCommonGoalScout(scout);
    }

    /**
     * Rejoin a player in the game after disconnecting.
     *
     * @param playerID The ID of the player to be reloaded.
     * @param client   The ClientHandler object associated with the player.
     */
    public void rejoin(String playerID, ClientHandler client) {
        this.players.put(playerID, client);
        try {
            this.gameModel.getPlayer(playerID).setStatus(true);
        } catch (PlayerNotFoundException e) {
            logger.severe(e.toString());
            try {
                client.remoteView().outcomeException(e);
            } catch (RemoteException ex) {
                logger.severe(ex.getMessage());
            }
        }
        for (ClientHandler clientHandler : players.values()) {
            try {
                clientHandler.remoteView().reloadPlayer(playerID);
            } catch (RemoteException e) {
                logger.severe(e.toString());
            }
        }
    }

    /**
     * Logs out a player from the game and updates their status.
     *
     * @param playerID The ID of the player to be logged out.
     * @return The ClientHandler object associated with the player.
     * @throws RemoteException If a remote communication error occurs.
     */
    public ClientHandler logOut(String playerID) throws RemoteException {
        try {
            this.gameModel.getPlayer(playerID).setStatus(false);
            if (this.gameModel.getCurrentPlayer().getPlayerID().equals(playerID) && this.turnPhase == TurnPhase.INSERTING)
                this.gameModel.completeTurn(this.currentPlayer.getTiles());
            ClientHandler crashed = this.players.get(playerID);
            this.players.put(playerID, null);
            for (ClientHandler client : players.values()) {
                if (client != null) {
                    Thread thread = new Thread(() -> {
                        try {
                            client.remoteView().crashedPlayer(playerID);
                        } catch (RemoteException e) {
                            logger.severe(e.toString());
                        }
                    });
                    thread.start();
                }
            }
            return crashed;
        } catch (PlayerException e) {
            logger.severe(e + " for logout");
            return null;
        }
    }

    /**
     * Checks if the specified player is able to perform an action based on the current turn phase.
     *
     * @param playerID The ID of the player to check.
     * @return The TurnPhase indicating the player's ability to perform an action.
     * @throws NotYourTurnException If it is not the specified player's turn.
     */
    private TurnPhase ableTo(String playerID) throws NotYourTurnException {
        if (!playerID.equals(this.currentPlayer.getCurrentPlayer().getPlayerID()))
            throw new NotYourTurnException(this.gameModel.getCurrentPlayer().getPlayerID());
        else return this.turnPhase;
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
     * Returns the list of players in the game.
     *
     * @return The list of players in the game.
     */
    public List<ClientHandler> getClients() {
        List<ClientHandler> clients = new ArrayList<>();
        for (ClientHandler client : this.players.values()) {
            if (client != null) clients.add(client);
        }
        return clients;
    }

    /**
     * Returns the map of players in the game.
     *
     * @return The map of players in the game.
     */
    public HashMap<String, ClientHandler> getPlayers() {
        return players;
    }
}