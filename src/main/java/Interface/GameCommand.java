package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import Enumeration.MessageType;
import Server.Network.Client.SocketHandler;
import Utils.Coordinates;
import Utils.Tile;

public interface GameCommand extends Remote {

    List<Tile> selectedTiles(String playerID, List<Coordinates> coordinates) throws RemoteException;

    boolean insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException;

    boolean writeChat(String playerID, String message) throws RemoteException;

    void liveStatus() throws RemoteException;
    
}