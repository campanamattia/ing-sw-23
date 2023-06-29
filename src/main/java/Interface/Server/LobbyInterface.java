package Interface.Server;

import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The LobbyInterface interface represents a remote interface for lobby operations.
 * It defines methods for retrieving lobby information, setting lobby size,
 * logging in/out of a lobby, and pinging players.
 */
public interface LobbyInterface extends Remote {

    /**
     * Retrieves lobby information and sends it to the remote view.
     *
     * @param remote the remote view to receive the lobby information.
     * @throws RemoteException if a remote communication error occurs.
     */
    void getLobbyInfo(RemoteView remote) throws RemoteException;

    /**
     * Sets the lobby size for the specified lobby.
     *
     * @param playerID the ID of the player setting the lobby size.
     * @param lobbyID the ID of the lobby to set the size for.
     * @param lobbySize the size to set for the lobby.
     * @throws RemoteException if a remote communication error occurs.
     */
    void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException;

    /**
     * Logs a player into a lobby and associates the remote view and remote client with the player.
     *
     * @param playerID the ID of the player to log in.
     * @param lobbyID the ID of the lobby to log into.
     * @param remoteView the remote view associated with the player.
     * @param network the remote client associated with the player.
     * @throws RemoteException if a remote communication error occurs.
     */
    void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient network) throws RemoteException;

    /**
     * Sends a ping message to the specified player in the lobby.
     *
     * @param playerID the ID of the player to ping.
     * @param lobbyID  the ID of the lobby where the player is located.
     * @throws RemoteException if a remote communication error occurs.
     */
    void ping(String playerID, String lobbyID) throws RemoteException;

    /**
     * Logs a player out of a lobby.
     *
     * @param playerID the ID of the player to log out.
     * @param lobbyID  the ID of the lobby to log out from.
     * @throws RemoteException if a remote communication error occurs.
     */
    void logOut(String playerID, String lobbyID) throws RemoteException;
}