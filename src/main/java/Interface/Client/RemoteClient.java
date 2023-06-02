package Interface.Client;

import Interface.Server.GameCommand;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {

    void pong(String playerID, String lobbyID) throws RemoteException;

    void setGameController(GameCommand gameController) throws RemoteException;
}
