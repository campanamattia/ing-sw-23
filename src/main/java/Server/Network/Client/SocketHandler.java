package Server.Network.Client;

import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Interface.Server.GameCommand;
import Messages.ClientMessage;
import Messages.Server.Network.UpdateMessage;
import Messages.Server.View.*;
import Messages.Server.Network.PongMessage;
import Messages.ServerMessage;
import Server.ServerApp;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;
import Utils.Rank;
import Utils.Tile;
import Enumeration.GameWarning;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;

import static Server.ServerApp.executorService;
import static Server.ServerApp.logger;

/**
 * This class represents the handler for each socket connection
 * It implements all the method that the server can call also on an RMI connection
 */
@SuppressWarnings("rawtypes")
public class SocketHandler implements Runnable, RemoteView, RemoteClient, Scout {
    private String playerID;
    private final Socket socket;
    @SuppressWarnings("FieldCanBeLocal")
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private GameCommand controller;

    /**
     * This constructor is used when the connection is a socket connection
     * @param socket the socket to communicate with the client
     */
    public SocketHandler(Socket socket) {
        this.controller = null;
        this.socket = socket;
    }

    /**
     * Runs the SocketHandler thread.
     * It sets up the input and output streams, and continuously listens for incoming messages from the client.
     * When a message is received, it deserializes it and executes the corresponding action.
     * If an IOException or ClassNotFoundException occurs, it throws a RuntimeException.
     */
    @Override
    public void run() {
        try{
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            executorService.execute(()-> {
                try {
                    askPlayerInfo(ServerApp.lobby.getLobbyInfo());
                } catch (RemoteException e) {
                    ServerApp.logger.log(Level.SEVERE, e.getMessage());
                }
            });
            //noinspection InfiniteLoopStatement
            while (true) {
                deserialize(in.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
        }
    }

    private void deserialize(Object message) {
        if(message instanceof ClientMessage clientMessage){
            clientMessage.execute(this);
        } else logger.log(Level.SEVERE, "Message not recognized");
    }

    /**
     * Notifies the client about a new turn.
     * Sends a ServerMessage of type NewTurnMessage to the client with the specified playerID.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param playerID the ID of the player who has the new turn
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void newTurn(String playerID) throws RemoteException {
        ServerMessage message = new NewTurnMessage(playerID);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Asks the client to provide the lobby size.
     * Sends a ServerMessage of type AskLobbySizeMessage to the client.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void askLobbySize() throws RemoteException {
        ServerMessage message = new AskLobbySizeMessage();
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Notifies the client about the outcome of selecting tiles.
     * Sends a ServerMessage of type OutcomeSelectTilesMessage to the client with the list of selected tiles.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param tiles the list of tiles selected
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void outcomeSelectTiles(List<Tile> tiles) throws RemoteException {
        ServerMessage message = new OutcomeSelectTilesMessage(tiles);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Notifies the client about the outcome of inserting tiles.
     * Sends a ServerMessage of type OutcomeInsertTilesMessage to the client with the specified success status. (Always true,
     * because if the insertion fails, the client is notified with an ErrorMessage)
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param success the success status of the tile insertion
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {
        ServerMessage message = new OutcomeInsertTilesMessage(success);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Notifies the client about an exception.
     * Sends a ServerMessage of type ErrorMessage to the client with the specified exception.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param e the exception to notify the client about
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void outcomeException(Exception e) throws RemoteException {
        ServerMessage message = new ErrorMessage(e);
        try {
            send(message);
        } catch (IOException ex) {
            ServerApp.logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * Notifies the client about the outcome of a login request
     * (only when the login is successful, otherwise the client is notified with an ErrorMessage).
     * Sends a ServerMessage of type OutcomeLoginMessage to the client with the specified local player and lobby ID.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param localPlayer the local player's name
     * @param lobbyID     the ID of the lobby the player has joined
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void outcomeLogin(String localPlayer, String lobbyID) throws RemoteException {
        this.playerID = localPlayer;
        ServerMessage message = new OutcomeLoginMessage(localPlayer, lobbyID);
        try {
            send(message);
        } catch (IOException ex) {
            ServerApp.logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * Asks the client for player information.
     * Sends a ServerMessage of type AskPlayerInfoMessage to the client with the specified lobby information.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param lobbyInfo the lobby information to ask the player for
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void askPlayerInfo(List<Map<String, String>> lobbyInfo) throws RemoteException {
        ServerMessage message = new AskPlayerInfoMessage(lobbyInfo);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Sends the entire game state to the client.
     * Sends a ServerMessage of type AllGameMessage to the client with the specified mock model representing the game state.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param mockModel the mock model representing the game state
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void allGame(MockModel mockModel) throws RemoteException {
        ServerMessage message = new AllGameMessage(mockModel);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Notifies the client about the end of the game and the leaderboard.
     * Sends a ServerMessage of type EndGameMessage to the client with the specified leaderboard.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param leaderboard the leaderboard of the game
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void endGame(List<Rank> leaderboard) throws RemoteException {
        ServerMessage message = new EndGameMessage(leaderboard);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Notifies the client about a crashed player.
     * Sends a ServerMessage of type CrashedPlayerMessage to the client with the specified crashed player ID.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param crashedPlayer the ID of the crashed player
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void crashedPlayer(String crashedPlayer) throws RemoteException {
        ServerMessage message = new CrashedPlayerMessage(crashedPlayer);
        try{
            send(message);
        } catch (IOException e){
            ServerApp.logger.severe(e.getMessage());
        }
    }

    /**
     * Notifies the client about the rejoining of a specific player.
     * Sends a ServerMessage of type ReloadPlayerMessage to the client with the ID of the player that rejoined the game.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param reloadPlayer the ID of the player that rejoined
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void reloadPlayer(String reloadPlayer) throws RemoteException {
        ServerMessage message = new ReloadPlayerMessage(reloadPlayer);
        try{
            send(message);
        } catch (IOException e){
            ServerApp.logger.severe(e.getMessage());
        }
    }

    @Override
    public void outcomeMessage(GameWarning message) throws RemoteException {
        ServerMessage serverMessage = new OutcomeMessage(message);
        try {
            send(serverMessage);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Sends a pong message to the client.
     * Sends a ServerMessage of type PongMessage to the client with the specified player ID and lobby ID.
     * If an IOException occurs while sending the message, it logs the error.
     *
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void pong(String playerID, String lobbyID) throws RemoteException {
        try {
            send(new PongMessage(playerID, lobbyID));
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Sets the game controller for the client.
     * Sets the specified game controller for the client and add the client as a scout to the game controller.
     * If a RemoteException occurs while adding the client as a scout, it logs the error.
     *
     * @param gameController the game controller to set
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void setGameController(GameCommand gameController) throws RemoteException {
        this.controller = gameController;
        executorService.execute(() ->{
            try {
                this.controller.addScout(this.playerID, this);
            } catch (RemoteException e) {
                ServerApp.logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    private synchronized void send(ServerMessage message) throws IOException {
        try {
            this.out.writeObject(message);
            this.out.flush();
            this.out.reset();
        } catch (IOException e) {
            logOut();
        }
    }

    /**
     * Logs out the client by closing the socket connection.
     * If an IOException occurs while closing the socket, it logs the error.
     */
    public void logOut() {
        try {
            socket.close();
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Updates the client with the specified object.
     * Sends an UpdateMessage to the client based on the type of the object.
     * If the object is of type MockBoard, MockPlayer, MockCommonGoal, or ChatMessage, it sends an UpdateMessage containing the object.
     * If the object is null or of an unknown type, it logs a severe-level message indicating the unknown object type.
     * If an exception occurs while sending the message, it logs the error.
     *
     * @param objects the object to update the client with
     * @throws RemoteException if there is a remote communication error
     */
    @Override
    public void update(Object objects) throws RemoteException {
        try {
            switch (objects) {
                case MockBoard mockBoard -> send(new UpdateMessage(mockBoard));
                case MockPlayer mockPlayer -> send(new UpdateMessage(mockPlayer));
                case MockCommonGoal mockCommonGoal -> send(new UpdateMessage(mockCommonGoal));
                case ChatMessage chatMessage -> send(new UpdateMessage(chatMessage));
                case null, default -> ServerApp.logger.log(Level.SEVERE, "Unknown object type");
            }
        } catch (Exception e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Gets the game controller associated with the client.
     *
     * @return the game controller associated with the client
     */
    public GameCommand getGameController() {
        return controller;
    }
}
