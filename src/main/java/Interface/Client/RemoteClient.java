package Interface.Client;

import Interface.Server.GameCommand;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RemoteClient interface represents a remote interface for a client.
 * It defines methods for sending a pong message and setting the game controller.
 */
public interface RemoteClient extends Remote {

    /**
     * Sends a pong message with the specified player ID and lobby ID.
     *
     * @param playerID the ID of the player.
     * @param lobbyID  the ID of the lobby.
     * @throws RemoteException if a remote communication error occurs.
     */
    void pong(String playerID, String lobbyID) throws RemoteException;

    /**
     * Sets the game controller for the client.
     *
     * @param gameController the game controller to set.
     * @throws RemoteException if a remote communication error occurs.
     */
    void setGameController(GameCommand gameController) throws RemoteException;
}
