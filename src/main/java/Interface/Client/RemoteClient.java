package Interface.Client;

import Server.Controller.GameController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {

    void pong() throws RemoteException;

    void setGameController(GameController gameController) throws Exception;
}
