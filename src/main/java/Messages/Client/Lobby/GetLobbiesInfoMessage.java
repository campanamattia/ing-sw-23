package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

/**
 * Represents a client message used to request information about available lobbies.
 * It is sent by a client to retrieve the information about all the lobbies on the server.
 */
public class GetLobbiesInfoMessage extends ClientMessage {

    /**
     * Executes the client message by invoking the appropriate method on the server to retrieve the lobby information.
     *
     * @param socket the SocketHandler used for communication with the server.
     */
    @Override
    public void execute(SocketHandler socket) {
        try {
            ServerApp.lobby.getLobbyInfo(socket);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.getMessage());
        }
    }
}
