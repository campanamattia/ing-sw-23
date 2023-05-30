package Client.Network;

import Client.View.View;
import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Messages.Client.GameController.InsertTilesMessage;
import Messages.Client.GameController.RegisterScout;
import Messages.Client.GameController.SelectedTilesMessage;
import Messages.Client.GameController.WriteChatMessage;
import Messages.Client.Lobby.*;
import Messages.ClientMessage;
import Messages.ServerMessage;

import Server.Controller.GameController;
import Utils.Coordinates;


import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class ClientSocket extends Network {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final AtomicBoolean clientConnected = new AtomicBoolean(false);

    public ClientSocket(View view) throws RemoteException {
        super(view);
        this.socket = null;
    }

    @Override
    public void init(String ipAddress, int port) {
        port = (port == -1) ? 50000 : port;
        try {
            this.socket = new Socket(ipAddress, port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");
            while(true){
                Object ob;
                ob = in.readObject();
                deserialize(ob);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection failed");
        }
    }

    private void deserialize(Object message) {
        if (message instanceof ServerMessage serverMessage) {
            serverMessage.execute(view);
        } else System.out.println("Message not recognized");
    }

    public void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        ClientMessage clientMessage = new SelectedTilesMessage(playerID, coordinates);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    public void writeChat(String from, String message, String to) throws RemoteException {
        ClientMessage clientMessage = new WriteChatMessage(from, message, to);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void addSubscriber(Scout scout) throws RemoteException {
        try {
            sendMessage(new RegisterScout("useless"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertTiles(String playerID, List<Integer> sorting, int column) throws RemoteException {
        ClientMessage clientMessage = new InsertTilesMessage(playerID, sorting, column);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void setGameController(GameController gameController) throws RemoteException {
    }

    @Override
    public void getLobbyInfo(RemoteView remote) throws RemoteException {
        ClientMessage clientMessage = new GetLobbiesInfoMessage();
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        ClientMessage clientMessage = new LobbySizeMessage(playerID, lobbyID, lobbySize);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient network) throws RemoteException {
        ClientMessage addedPlayerMessage = new AddPlayerMessage(playerID, lobbyID);
        try {
            sendMessage(addedPlayerMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void ping(String playerID, String lobbyID) throws RemoteException {
        ClientMessage clientMessage = new PingMessage(playerID, lobbyID);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void getGameController(String lobbyID, RemoteClient remote) {
        ClientMessage clientMessage = new GetGameMessage(lobbyID);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void logOut(String playerID, String lobbyID) throws RemoteException {
        ClientMessage clientMessage = new LogOutMessage(playerID, lobbyID);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
        System.exit(-1);
    }

    private void sendMessage(ClientMessage clientMessage) throws IOException {
        try {
            this.out.writeObject(clientMessage);
            this.out.flush();
            this.out.reset();
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }
}