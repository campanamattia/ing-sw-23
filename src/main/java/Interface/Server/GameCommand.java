package Interface.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Interface.Scout;
import Utils.Coordinates;

public interface GameCommand extends Remote {

    void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException;

    void insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException;

    void writeChat(String from, String message, String to) throws RemoteException;

    void addScout(String playerID, Scout scout) throws RemoteException;
}