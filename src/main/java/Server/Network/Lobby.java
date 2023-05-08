package Server.Network;

import Interface.View;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;


public class Lobby extends UnicastRemoteObject implements Interface.Lobby {
    private HashMap<String, ClientHandler> lobby;
    private List<GameController> games;
    private int lobbySize;

    public Lobby() throws RemoteException {
        super();
        this.lobby = new HashMap<>();
        this.lobbySize = -1;
        this.games = null;
    }


    public void setLobbySize(int lobbySize){
        this.lobbySize = lobbySize;
    }

    @Override
    public boolean LogIn(String playerID, View view) throws RemoteException {
        return true;
    }

    @Override
    public boolean LogOut(String playerID) throws RemoteException {
        return false;
    }

    public boolean LogIn(String playerUD, SocketHandler socketHandler) {
        return true;
    }
}
