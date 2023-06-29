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


@SuppressWarnings("BlockingMethodInNonBlockingContext")
public class ClientSocket extends Network {
    private Socket socket;
    @SuppressWarnings("FieldCanBeLocal")
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final AtomicBoolean clientConnected = new AtomicBoolean(true);

    public ClientSocket() throws RemoteException {
        super();
        this.socket = null;
    }

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
            quit(404);
        }
    }

    private void deserialize(Object message) {
        if (message instanceof ServerMessage serverMessage) {
            serverMessage.execute(view);
        } else System.out.println("Message not recognized");
    }

    public synchronized void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        ClientMessage clientMessage = new SelectedTilesMessage(playerID, coordinates);
        sendMessage(clientMessage);
    }

    public synchronized void writeChat(String from, String message, String to) throws RemoteException {
        ClientMessage clientMessage = new WriteChatMessage(from, message, to);
        sendMessage(clientMessage);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public synchronized void addScout(String playerID,  Scout scout) throws RemoteException {
        //never called
    }

    @Override
    public synchronized void insertTiles(String playerID, List<Integer> sorting, int column) throws RemoteException {
        ClientMessage clientMessage = new InsertTilesMessage(playerID, sorting, column);
        sendMessage(clientMessage);
    }

    @Override
    public synchronized void setGameController(GameCommand gameController) throws RemoteException {
        //never called
    }

    @Override
    public synchronized void getLobbyInfo(RemoteView remote) throws RemoteException {
        ClientMessage clientMessage = new GetLobbiesInfoMessage();
        sendMessage(clientMessage);
    }

    @Override
    public synchronized void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        ClientMessage clientMessage = new LobbySizeMessage(playerID, lobbyID, lobbySize);
        sendMessage(clientMessage);
    }

    @Override
    public synchronized void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient network) throws RemoteException {
        ClientMessage clientMessage = new AddPlayerMessage(playerID, lobbyID);
        sendMessage(clientMessage);
    }

    @Override
    public synchronized void ping(String playerID, String lobbyID) throws RemoteException {
        ClientMessage clientMessage = new PingMessage(playerID, lobbyID);
        sendMessage(clientMessage);
    }


    @Override
    public synchronized void logOut(String playerID, String lobbyID) throws RemoteException {
        ClientMessage clientMessage = new LogOutMessage(playerID, lobbyID);
        sendMessage(clientMessage);
        quit(404);
    }

    private synchronized void sendMessage(ClientMessage clientMessage) {
        try {
            this.out.writeObject(clientMessage);
            this.out.flush();
            this.out.reset();
        } catch (IOException e) {
            clientConnected.set(false);
            quit(404);
        }
    }
}