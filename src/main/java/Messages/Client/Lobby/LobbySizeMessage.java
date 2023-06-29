package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

/**
 * Represents a client message used to set the size of a lobby.
 * It is sent by a client to specify the desired size for a specific lobby.
 */
public class LobbySizeMessage extends ClientMessage {
    private final String lobbyID;
    private final int lobbySize;

    /**
     * Constructs a LobbySizeMessage with the specified player ID, lobby ID, and lobby size.
     *
     * @param playerID   the ID of the player sending the message.
     * @param lobbyID    the ID of the lobby to set the size for.
     * @param lobbySize  the desired size for the lobby.
     */
    public LobbySizeMessage(String playerID, String lobbyID, int lobbySize) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
        this.lobbySize = lobbySize;
    }

    /**
     * Executes the client message by invoking the appropriate method on the server to set the lobby size.
     *
     * @param socket the SocketHandler used for communication with the server.
     */
    @Override
    public void execute(SocketHandler socket) {
        try {
            ServerApp.lobby.setLobbySize(this.playerID, this.lobbyID, this.lobbySize);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
