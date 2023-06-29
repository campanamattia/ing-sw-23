package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

/**
 * Represents a client message used for logging out from a lobby.
 * It is sent by a client to request logging out from a specific lobby.
 */
public class LogOutMessage extends ClientMessage {
    private final String lobbyID;

    /**
     * Constructs a LogOutMessage with the specified player ID and lobby ID.
     *
     * @param playerID the ID of the player logging out.
     * @param lobbyID  the ID of the lobby to log out from.
     */public LogOutMessage(String playerID, String lobbyID) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    /**
     * Executes the client message by invoking the appropriate method on the server to log out from the lobby.
     *
     * @param socket the SocketHandler used for communication with the server.
     */
    @Override
    public void execute(SocketHandler socket) {
        try {
            ServerApp.lobby.logOut(playerID, lobbyID);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
