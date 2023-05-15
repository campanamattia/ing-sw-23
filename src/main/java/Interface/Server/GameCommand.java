package Interface.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Client.Network.Network;
import Utils.Coordinates;
import Utils.Tile;

public interface GameCommand extends Remote {

    void selectedTiles(String playerID, List<Coordinates> coordinates) throws RemoteException;

    void insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException;

    void writeChat(String playerID, String message) throws RemoteException;

    void liveStatus(String playerID) throws RemoteException;

    void logOut(String playerID) throws RemoteException, PlayerNotFoundException;

    void addSubscriber(Object object) throws RemoteException;

}