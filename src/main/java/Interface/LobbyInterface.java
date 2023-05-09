package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LobbyInterface extends Remote {

    void setLobbySize(String playerID, int lobbySize) throws RemoteException;

    void logIn(String playerID, RemoteView remoteView) throws RemoteException;

    void logOut(String playerID) throws RemoteException;

    default void ping() throws RemoteException{
        //return MessageType.PONG;
    };
}