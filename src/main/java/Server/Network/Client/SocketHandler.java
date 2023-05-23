package Server.Network.Client;

import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Messages.Server.UpdateMessage;
import Messages.Server.View.ErrorMessage;
import Messages.Server.Network.PongMessage;
import Messages.ServerMessage;
import Server.Controller.GameController;
import Server.ServerApp;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;
import Utils.Rank;
import Utils.Tile;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;


public class SocketHandler implements Runnable, RemoteView, RemoteClient, Scout {
    private final Socket socket;
    private Scanner input;
    private final ExecutorService executorService;
    private GameController controller;



    public SocketHandler(Socket socket) {
        this.controller = null;
        this.executorService = Executors.newCachedThreadPool();
        this.socket = socket;
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

            while (!this.socket.isClosed()) {
                if(input.hasNextLine()) {
                    String line = input.nextLine();
                    this.executorService.submit(() -> {
                        deserialize(line);
                    });
                }
            }
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    // TODO: 16/05/2023  
    private void deserialize(String line) {

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
    public void outcomeException(Exception e) throws RemoteException {

    }

    @Override
    public void outcomeLogin(String localPlayer, String lobbyID) throws RemoteException {
        
    }

    @Override
    public void askPlayerInfo(List<Collection<String>> lobbyInfo) throws RemoteException {

    }

    @Override
    public void allGame(MockModel mockModel) throws RemoteException {

    }

    @Override
    public void endGame(List<Rank> leaderboard) throws RemoteException {

    }

    @Override
    public void crashedPlayer(String crashedPlayer) throws RemoteException {

    }

    @Override
    public void reloadPlayer(String reloadPlayer) throws RemoteException {

    }

    @Override
    public void pong() throws RemoteException {
        try {
            send(new PongMessage());
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void setGameController(GameController gameController) throws RemoteException {
        this.controller = gameController;
    }

    public GameController getController() {
        return controller;
    }

    private void send(ServerMessage output) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(output);
        out.flush();
    }

    public void logOut() {
        this.input.close();
        try {
            socket.close();
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
        this.executorService.shutdown();
    }

    @Override
    public void update(Object objects) throws RemoteException {
        try {
            if(objects instanceof MockBoard)
                send(new UpdateMessage((MockBoard) objects));
            else if (objects instanceof MockPlayer)
                send(new UpdateMessage((MockPlayer) objects));
            else if (objects instanceof MockCommonGoal)
                send(new UpdateMessage((MockCommonGoal) objects));
            else if (objects instanceof ChatMessage)
                send(new UpdateMessage((ChatMessage) objects));
            else ServerApp.logger.log(Level.SEVERE, "Unknown object type");
        } catch (Exception e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
