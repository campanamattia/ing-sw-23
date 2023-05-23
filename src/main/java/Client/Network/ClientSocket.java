package Client.Network;

import Client.View.View;
import Enumeration.OperationType;
import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Messages.Client.GameController.InsertTilesMessage;
import Messages.Client.GameController.SelectedTilesMessage;
import Messages.Client.GameController.WriteChatMessage;
import Messages.ClientMessage;
import Messages.Server.View.AddedPlayerMessage;
import Messages.ServerMessage;

import Server.Controller.GameController;
import Utils.ChatMessage;
import Utils.Coordinates;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientSocket extends Network{
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final AtomicBoolean clientConnected = new AtomicBoolean(false);

    private final Thread messageListener;
    private ExecutorService executorService;


    public ClientSocket(View view) throws IOException {
        super(view);
        messageListener = new Thread(this::readMessages);
    }

    public void init(int port, String ipAddress) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(ipAddress, port));
        this.port = port;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        this.ipAddress = ipAddress;
        clientConnected.set(true);
        messageListener.start();

    }

    public void readMessages(){
        try{
            while (clientConnected.get()) {
               String input = inputStream.readObject().toString();

               this.executorService.submit(() -> {
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

    public void writeChat(String playerID, String text) throws RemoteException {
        ClientMessage clientMessage = new WriteChatMessage(playerID, text);
        try {
            sendMessage(clientMessage);
        } catch (IOException e) {
            clientConnected.set(false);
        }
    }

    @Override
    public void addSubscriber(Object object) throws RemoteException {

    }

    @Override
    public void insertTiles(String playerID, List<Integer> sorted, int column) throws RemoteException{
        OperationType operationType = OperationType.INSERTTILES;
        ClientMessage clientMessage = new InsertTilesMessage(playerID, sorted, column);
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

    @Override
    public void init(String ipAddress, int ip) throws IOException {

    }

    @Override
    public void pong() throws RemoteException {

    }

    @Override
    public void setGameController(GameController gameController) throws RemoteException {

    }

    @Override
    public void getLobbyInfo(RemoteView remote) throws RemoteException {

    }

    @Override
    public void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {

    }

    @Override
    public void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient network) throws RemoteException {
        AddedPlayerMessage addedPlayerMessage = new AddedPlayerMessage(playerID, lobbyID);
    }

    @Override
    public void ping(String playerID, String lobbyID) throws RemoteException {

    }

    @Override
    public void getGameController(String lobbyID, RemoteClient remote) throws Exception {

    }

    @Override
    public void logOut(String playerID, String lobbyID) throws RemoteException {

    }

    @Override
    public void update(Object objects) throws RemoteException {
        if(scouts.containsKey(objects.getClass())){
            scouts.get(objects.getClass()).update(objects);
        } else {
            throw new RemoteException("Scout not found");
        }
    }
}