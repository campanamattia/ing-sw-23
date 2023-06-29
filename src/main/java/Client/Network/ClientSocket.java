package Client.Network;

import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Interface.Server.GameCommand;
import Messages.Client.GameController.InsertTilesMessage;
import Messages.Client.GameController.SelectedTilesMessage;
import Messages.Client.GameController.WriteChatMessage;
import Messages.Client.Lobby.*;
import Messages.ClientMessage;
import Messages.ServerMessage;

import Utils.Coordinates;


import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static Client.ClientApp.*;


/**
 The {@code ClientSocket} class represents a client's network implementation using Socket for client-server communication.
 It extends the {@code Network} class and provides methods to initialize the socket connection, send messages to the server,
 and handle incoming messages from the server.
 */
public class ClientSocket extends Network {
    private Socket socket;
    @SuppressWarnings("FieldCanBeLocal")
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final AtomicBoolean clientConnected = new AtomicBoolean(true);

    /**
     Constructs a new {@code ClientSocket} instance.
     @throws RemoteException if a remote communication error occurs
     */
    public ClientSocket() throws RemoteException {
        super();
        this.socket = null;
    }

    /**
     Initializes the socket connection and starts listening for incoming messages from the server.
     */
    @Override
    public void init() {
        try {
            this.socket = new Socket(IP_SERVER, SOCKET_PORT);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");
            while(clientConnected.get()){
                Object ob;
                ob = in.readObject();
                executorService.execute(()->deserialize(ob));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection failed");
        }
    }

    /**
     Deserializes and handles the incoming server message.
     @param message the incoming server message
     */
    private void deserialize(Object message) {
        if (message instanceof ServerMessage serverMessage) {
            serverMessage.execute(view);
        } else System.out.println("Message not recognized");
    }

    /**
     Sends a selected tiles message to the server.
     @param playerID the player ID
     @param coordinates the list of selected tile coordinates
     @throws RemoteException if a remote communication error occurs
     */
    public synchronized void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        ClientMessage clientMessage = new SelectedTilesMessage(playerID, coordinates);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    /**
     Sends a chat message to the server.
     @param from the sender's name
     @param message the chat message
     @param to the recipient's name
     @throws RemoteException if a remote communication error occurs
     */
    public synchronized void writeChat(String from, String message, String to) throws RemoteException {
        ClientMessage clientMessage = new WriteChatMessage(from, message, to);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    /**
     * Adds a scout to the network.
     *
     * @param playerID the ID of the player
     * @param scout    the scout to add
     * @throws RemoteException if a remote communication error occurs
     */
    @SuppressWarnings("rawtypes")
    @Override
    public synchronized void addScout(String playerID,  Scout scout) throws RemoteException {
        //never called
    }

    /**
     * Sends a message to insert tiles into a specific column.
     *
     * @param playerID the ID of the player
     * @param sorting  the list of tile sorting indexes
     * @param column   the column number
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void insertTiles(String playerID, List<Integer> sorting, int column) throws RemoteException {
        ClientMessage clientMessage = new InsertTilesMessage(playerID, sorting, column);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }


    @Override
    public synchronized void setGameController(GameCommand gameController) throws RemoteException {
        //never called
    }

    /**
     * Sends a request to the server to get the lobby information.
     *
     * @param remote the remote view to update with the lobby information
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void getLobbyInfo(RemoteView remote) throws RemoteException {
        ClientMessage clientMessage = new GetLobbiesInfoMessage();
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    /**
     * Sets the size of the lobby.
     *
     * @param playerID  the ID of the player
     * @param lobbyID   the ID of the lobby
     * @param lobbySize the size of the lobby
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        ClientMessage clientMessage = new LobbySizeMessage(playerID, lobbyID, lobbySize);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    /**
     * Logs a player into the lobby.
     * @param playerID    the ID of the player
     * @param lobbyID     the ID of the lobby
     * @param remoteView  the remote view associated with the player
     * @param network     the remote client network interface
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient network) throws RemoteException {
        ClientMessage addedPlayerMessage = new AddPlayerMessage(playerID, lobbyID);
        try {
            sendMessage(addedPlayerMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    /**
     * Sends a ping message to the server to check the connection status.
     *
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void ping(String playerID, String lobbyID) throws RemoteException {
        ClientMessage clientMessage = new PingMessage(playerID, lobbyID);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }


    /**
     * Logs out the player from the lobby and exits the client application.
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public synchronized void logOut(String playerID, String lobbyID) throws RemoteException {
        ClientMessage clientMessage = new LogOutMessage(playerID, lobbyID);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
        System.exit(-1);
    }

    /**
     * Sends a client message to the server.
     * @param clientMessage the client message to send
     * @throws IOException if an I/O error occurs
     */
    private synchronized void sendMessage(ClientMessage clientMessage) throws IOException {
        try {
            this.out.writeObject(clientMessage);
            this.out.flush();
            this.out.reset();
        } catch (IOException e) {
            clientConnected.set(false);
            System.exit(404);
        }
    }
}