package Server.Network.Client;

import Interface.Scout.BoardScout;
import Interface.Scout.CommonGoalScout;
import Interface.Scout.PlayerScout;
import Messages.ClientMessage;
import Messages.Server.ErrorMessage;
import Messages.ServerMessage;
import Server.Controller.Players.PlayersHandler;
import Server.Network.Lobby;
import Server.Network.LogOutTimer;
import Server.ServerApp;
import Utils.ChatMessage;
import Utils.ClientMessageFactory;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;
import Utils.Tile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;


public class SocketHandler extends ClientHandler implements Runnable, PlayerScout, BoardScout, CommonGoalScout {
    private final Socket socket;
    private Scanner input;
    private final ExecutorService executorService;
    private final Lobby lobby;
    private PlayersHandler playersHandler;

    public SocketHandler(Socket socket, Lobby lobby) {
        this.playerID = null;
        this.playersHandler = null;
        this.executorService = Executors.newCachedThreadPool();
        this.socket = socket;
        this.lobby = lobby;
        try {
            this.input = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            try {
                send(new ErrorMessage(new RuntimeException("Server is not ready yet")));
            } catch (IOException ex) {
                ServerApp.logger.log(Level.SEVERE, ex.toString());
            }
        }
    }

    @Override
    public void run() {
        try {
            input = new Scanner(socket.getInputStream());
            Timer timer = new Timer();

            while (!this.socket.isClosed()) {
                String line = input.nextLine();
                timer.cancel();
                timer = new Timer();
                timer.schedule(new LogOutTimer(this.playersHandler, this.playerID),10000);
                this.executorService.submit(() -> {
                    ClientMessage input = deserialize(line);
                    try {
                        if(this.playersHandler != null || this.lobby != null)
                            input.execute(this);
                        else
                            send(new ErrorMessage(new RuntimeException("Server is not ready yet")));
                    } catch (IOException e) {
                        ServerApp.logger.log(Level.SEVERE, e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void send(ServerMessage output) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(output);
        out.flush();
    }

    private ClientMessage deserialize(String line) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(line));
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        return ClientMessageFactory.getClientMessage(jsonObject.get("OperationType").getAsString(), line);
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setPlayersHandler(PlayersHandler playersHandler) {
        this.playersHandler = playersHandler;
    }

    public PlayersHandler getPlayersHandler() {
        return playersHandler;
    }

    @Override
    public void setPlayerID(String playerID) {
        super.setPlayerID(playerID);
    }
    @Override
    public String getPlayerID() {
        return super.getPlayerID();
    }

    @Override
    public void updateBoard(MockBoard mockBoard) throws RemoteException {

    }

    @Override
    public void updateCommonGoal(List<MockCommonGoal> mockCommonGoals) throws RemoteException {

    }

    @Override
    public void updatePlayer(MockPlayer mockPlayer) throws RemoteException {

    }

    @Override
    public void updateChat(Stack<ChatMessage> chatFlow) throws RemoteException {

    }

    @Override
    public void updateLobby(String playerLogged) throws RemoteException {

    }

    @Override
    public void newTurn(String playerID) throws RemoteException {

    }

    @Override
    public void askLobbySize() throws RemoteException {

    }

    @Override
    public void outcomeSelectTiles(List<Tile> tiles) throws RemoteException {

    }

    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeWriteChat(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeException(Exception e) throws RemoteException {

    }

    @Override
    public void outcomeLogin(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeLogout(boolean success) throws RemoteException {

    }

    @Override
    public void askPlayerID() throws RemoteException {

    }

    @Override
    public void update(MockBoard mockBoard) throws RemoteException {

    }

    @Override
    public void update(MockCommonGoal mockCommonGoal) throws RemoteException {

    }

    @Override
    public void update(MockPlayer mockPlayer) throws RemoteException {

    }

    @Override
    public void update(Object object) {

    }

    private void logOut() {
        if(playersHandler != null) {
            try {
                this.playersHandler.logOut(this.playerID);
            } catch (RemoteException e) {
                ServerApp.logger.log(Level.SEVERE, e.getMessage());
            }
        } else {
            try {
                this.lobby.logOut(this.playerID);
            } catch (RemoteException e) {
                ServerApp.logger.log(Level.SEVERE, e.getMessage());
            }
        }
        this.input.close();
        try {
            socket.close();
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
        this.executorService.shutdown();
    }
}
