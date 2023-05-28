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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class ClientSocket extends Network {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final AtomicBoolean clientConnected = new AtomicBoolean(false);

    public ClientSocket(View view) throws RemoteException {
        super(view);
        this.socket = null;
    }

    @Override
    public void init(String ipAddress, int port) {
        port = (port == -1) ? 50000 : port;
        try {
            this.socket = new Socket();
            socket.connect(new InetSocketAddress(ipAddress, port));
            if (socket.isConnected()) {
                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                clientConnected.set(true);
                this.executor.submit(this::readMessages);
            } else System.out.println(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readMessages() {
        try {
            while (clientConnected.get()) {
                Object input = inputStream.readObject();
                if (input instanceof ServerMessage message) {
                    this.executor.submit(() -> deserialize((message)));
                } else {
                    System.err.println("Received an unexpected object from the server.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            clientConnected.set(false);
            try {
                logOut(null, null);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
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
        if (clientConnected.get()) {
            try {
                outputStream.writeObject(clientMessage);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException e) {
                clientConnected.set(false);
            }
        }
    }


    private void deserialize(ServerMessage message) {
        message.execute(this.view);
    }
}