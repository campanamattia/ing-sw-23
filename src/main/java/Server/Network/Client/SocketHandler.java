package Server.Network.Client;

import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Interface.Server.GameCommand;
import Messages.ClientMessage;
import Messages.Server.Network.UpdateMessage;
import Messages.Server.View.*;
import Messages.Server.Network.PongMessage;
import Messages.ServerMessage;
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
import java.util.logging.Level;

import static Server.ServerApp.executorService;


public class SocketHandler implements Runnable, RemoteView, RemoteClient, Scout {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private GameCommand controller;



    public SocketHandler(Socket socket) {
        this.controller = null;
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            executorService.execute(()-> {
                try {
                    askPlayerInfo(ServerApp.lobby.getLobbyInfo());
                } catch (RemoteException e) {
                    ServerApp.logger.log(Level.SEVERE, e.getMessage());
                }
            });
            while (true) {
                deserialize(in.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deserialize(Object message) {
        if(message instanceof ClientMessage clientMessage){
            clientMessage.execute(this);
        } else ServerApp.logger.log(Level.SEVERE, "Message not recognized");
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
    public void setGameController(GameCommand gameController) throws RemoteException {
        ServerApp.logger.info("Setting game controller");
        this.controller = gameController;
        executorService.execute(() ->{
            try {
                this.controller.addScout(this);
            } catch (RemoteException e) {
                ServerApp.logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    private synchronized void send(ServerMessage message) throws IOException {
        try {
            this.out.writeObject(message);
            this.out.flush();
            this.out.reset();
        } catch (IOException e) {
            logOut();
        }
    }

    public void logOut() {
        try {
            socket.close();
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.getMessage());
        }
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

    public GameCommand getGameController() {
        return controller;
    }
}
