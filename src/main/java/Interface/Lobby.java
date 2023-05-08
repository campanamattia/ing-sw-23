package Interface;

import Enumeration.MessageType;
import Server.Network.Client.SocketHandler;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Lobby extends Remote {

    boolean LogIn(String playerID, View view) throws RemoteException;

    boolean LogOut(String playerID) throws RemoteException;

    default MessageType ping() throws RemoteException{
        return MessageType.PONG;
    };
}