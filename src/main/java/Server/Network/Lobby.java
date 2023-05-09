package Server.Network;

import Interface.LobbyInterface;
import Interface.RemoteView;
import Server.Controller.GameController;
import Server.Network.Client.RMIHandler;
import Server.Network.Client.SocketHandler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;


public class Lobby extends UnicastRemoteObject implements LobbyInterface {
    private HashMap<String, ClientHandler> lobby;
    private List<GameController> games;
    private int lobbySize;

    public Lobby() throws RemoteException {
        super();
        this.lobby = new HashMap<>();
        this.lobbySize = -1;
        this.games = null;
    }


    @Override
    public void setLobbySize(String playerID, int lobbySize) throws RemoteException {
        if(lobbySize>=2 && lobbySize<=4)
            this.lobbySize = lobbySize;
        else {
            this.lobby.get(playerID).outcomeException(new RuntimeException("Lobby size must be between 2 and 4"));
            this.lobby.get(playerID).askLobbySize();
        }
    }

    @Override
    public void logIn(String playerID, RemoteView remoteView) throws RemoteException {
        if(this.lobby.containsKey(playerID)){
            remoteView.outcomeException(new RuntimeException("PlayerID already taken"));
            remoteView.askPlayerID();
        }else{
            this.lobby.put(playerID, new RMIHandler(playerID, remoteView));

            if(this.lobby.size() == this.lobbySize){
                this.initGame();
            }
        }
    }

    @Override
    public void logOut(String playerID) throws RemoteException {
        ClientHandler clientHandler = this.lobby.remove(playerID);
        if(clientHandler != null){
            clientHandler.logOut();
        }
    }

    public void logIn(String playerID, SocketHandler socketHandler) {
        if(this.lobby.containsKey(playerID)){
            socketHandler.outcomeException(new RuntimeException("PlayerID already taken"));
            socketHandler.askPlayerID();
        }else{
            this.lobby.put(playerID, socketHandler);
            socketHandler.setPlayerID(playerID);
            firstPlayer(playerID);
            initGame();
        }
    }

    private void firstPlayer(String playerID){
        if(this.lobby.size() == 1){
            this.lobby.get(playerID).askLobbySize();
        }
    }

    private void initGame(){
        if(lobbySize == lobby.size()){
            games.add(new GameController(this.lobby));
            this.lobby = new HashMap<>();
            this.lobbySize = -1;
        }
    }
}
