package Interface.Server;

import Interface.Client.RemoteView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

public interface LobbyInterface extends Remote {

    List<Collection<String>> getLobbyInfo() throws RemoteException;

    void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException;

    void logIn(String playerID, String lobbyID, RemoteView remoteView) throws RemoteException;

    void ping(String playerID, String lobbyID) throws RemoteException;

    void logOut(String playerID, String lobbyID) throws RemoteException;
}