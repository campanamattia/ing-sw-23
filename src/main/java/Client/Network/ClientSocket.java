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

public class ClientSocket extends Network{
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final AtomicBoolean clientConnected = new AtomicBoolean(false);

    private final Thread messageListener;


    public ClientSocket(View view) throws IOException {
        super(view);
        messageListener = new Thread(this::readMessages);
    }

    @Override
    public void init(String ipAddress, int port) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ipAddress, port));
            this.port = port;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            this.ipAddress = ipAddress;
            clientConnected.set(true);
            messageListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readMessages(){
        try{
            while (clientConnected.get()) {
               String input = inputStream.readObject().toString();
               this.executor.submit(() -> {
                   deserialize(input);
               });
            }
        }  catch (IOException | ClassNotFoundException e) {
            clientConnected.set(false);
        }
    }

    public void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        ClientMessage clientMessage = new SelectedTilesMessage(playerID, coordinates);
        try{
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
    public void insertTiles(String playerID, List<Integer> sorting, int column) throws RemoteException{
        ClientMessage clientMessage = new InsertTilesMessage(playerID, sorting, column);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void setGameController(GameController gameController) throws RemoteException {
        return;
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
    public void getGameController(String lobbyID, RemoteClient remote) throws Exception {
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
    }

    private void sendMessage(ClientMessage clientMessage) throws IOException {
        if(clientConnected.get()) {
            try{
                outputStream.writeObject(clientMessage);
                outputStream.flush();
                outputStream.reset();
            } catch (IOException e ){
                clientConnected.set(false);
            }
        }
    }

    private void deserialize(String line) {
        try {
            ServerMessage message = (ServerMessage) new ObjectInputStream(new FileInputStream(line)).readObject();
            message.execute(view);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}