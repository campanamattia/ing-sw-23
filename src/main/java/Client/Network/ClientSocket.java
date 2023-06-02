package Client.Network;

import Client.View.View;
import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Interface.Server.GameCommand;
import Messages.Client.GameController.InsertTilesMessage;
import Messages.Client.GameController.RegisterScout;
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
import static Client.ClientApp.executorService;
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
                executorService.execute(()->deserialize(ob));
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

    public synchronized void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        view.printMessage("Selecting tiles");
        ClientMessage clientMessage = new SelectedTilesMessage(playerID, coordinates);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    public synchronized void writeChat(String from, String message, String to) throws RemoteException {
        ClientMessage clientMessage = new WriteChatMessage(from, message, to);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public synchronized void addSubscriber(Scout scout) throws RemoteException {
        try {
            sendMessage(new RegisterScout("useless"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void insertTiles(String playerID, List<Integer> sorting, int column) throws RemoteException {
        view.printMessage("Inserting tiles");
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

    @Override
    public synchronized void getLobbyInfo(RemoteView remote) throws RemoteException {
        ClientMessage clientMessage = new GetLobbiesInfoMessage();
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public synchronized void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        ClientMessage clientMessage = new LobbySizeMessage(playerID, lobbyID, lobbySize);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public synchronized void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient network) throws RemoteException {
        ClientMessage addedPlayerMessage = new AddPlayerMessage(playerID, lobbyID);
        try {
            sendMessage(addedPlayerMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public synchronized void ping(String playerID, String lobbyID) throws RemoteException {
        ClientMessage clientMessage = new PingMessage(playerID, lobbyID);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public synchronized void getGameController(String lobbyID, RemoteClient remote) {
        ClientMessage clientMessage = new GetGameMessage(lobbyID);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

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

    private synchronized void sendMessage(ClientMessage clientMessage) throws IOException {
        view.printMessage(clientMessage.getClass().getSimpleName());
        try {
            this.out.writeObject(clientMessage);
            this.out.flush();
            this.out.reset();
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }
}