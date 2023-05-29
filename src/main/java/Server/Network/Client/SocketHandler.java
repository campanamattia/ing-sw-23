package Server.Network.Client;

import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Messages.ClientMessage;
import Messages.Server.Network.UpdateMessage;
import Messages.Server.View.*;
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


import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;


public class SocketHandler implements Runnable, RemoteView, RemoteClient, Scout {
    private final Socket socket;
    private String lobbyID = null;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final ExecutorService executorService;
    private GameController controller;



    public SocketHandler(Socket socket) {
        this.controller = null;
        this.executorService = Executors.newCachedThreadPool();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                try {
                    send(new ErrorMessage(new RuntimeException("Server is not ready yet")));
                } catch (IOException ex) {
                    ServerApp.logger.log(Level.SEVERE, ex.toString());
                }
            }
            ServerApp.logger.info("trying to set up new connection");
            send(new AskPlayerInfoMessage(ServerApp.lobby.getLobbyInfo()));
            while (this.socket.isConnected()) {
                try {
                    Object object = input.readObject();
                    if(object instanceof ClientMessage message)
                        this.executorService.submit(()->deserialize(message));
                    else ServerApp.logger.severe("incorrect object in input");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage() + "\tIO MAIN THREAD");
        }
    }

    private void deserialize(ClientMessage message) {
        message.execute(this);
    }
    

    @Override
    public void newTurn(String playerID) throws RemoteException {
        ServerMessage message = new NewTurnMessage(playerID);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void askLobbySize() throws RemoteException {
        ServerMessage message = new AskLobbySizeMessage();
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void outcomeSelectTiles(List<Tile> tiles) throws RemoteException {
        ServerMessage message = new OutcomeSelectTilesMessage(tiles);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {
        ServerMessage message = new OutcomeInsertTilesMessage(success);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }
    

    @Override
    public void outcomeException(Exception e) throws RemoteException {
        ServerMessage message = new ErrorMessage(e);
        try {
            send(message);
        } catch (IOException ex) {
            ServerApp.logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void outcomeLogin(String localPlayer, String lobbyID) throws RemoteException {
        ServerMessage message = new OutcomeLoginMessage(localPlayer, lobbyID);
        try {
            send(message);
        } catch (IOException ex) {
            ServerApp.logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void askPlayerInfo(List<Map<String, String>> lobbyInfo) throws RemoteException {
        ServerMessage message = new AskPlayerInfoMessage(lobbyInfo);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void allGame(MockModel mockModel) throws RemoteException {
        ServerMessage message = new AllGameMessage(mockModel);
        try {
            send(message);
            this.executorService.submit(()-> {
                try {
                    ServerApp.lobby.getGameController(this.lobbyID, this);
                } catch (RemoteException e) {
                    ServerApp.logger.log(Level.SEVERE, e.getMessage());
                }
            });
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void endGame(List<Rank> leaderboard) throws RemoteException {
        ServerMessage message = new EndGameMessage(leaderboard);
        try {
            send(message);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void crashedPlayer(String crashedPlayer) throws RemoteException {
        ServerMessage message = new CrashedPlayerMessage(crashedPlayer);
        try{
            send(message);
        } catch (IOException e){
            ServerApp.logger.severe(e.getMessage());
        }
    }

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
    public void pong(String playerID, String lobbyID) throws RemoteException {
        try {
            send(new PongMessage(playerID, lobbyID));
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

    private void send(ServerMessage message) throws IOException {
        try {
            this.output.reset();
            ServerApp.logger.info("Sending: "+message);
            this.output.writeObject(message);
            this.output.flush();
        } catch (IOException e) {
            logOut();
        }
    }

    public void logOut() {
        try {
            this.input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public GameController getGameController() {
        return controller;
    }

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }
}
