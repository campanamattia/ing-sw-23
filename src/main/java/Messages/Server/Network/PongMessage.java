package Messages.Server.Network;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;
import static Client.ClientApp.network;

/**
 * Represents a server message indicating a Pong response from the server.
 * This message is used to acknowledge a Ping message sent by the client.
 */
public class PongMessage extends ServerMessage {
    private final String playerID;
    private final String lobbyID;

    /**
     * Constructs a new PongMessage with the specified player ID and lobby ID.
     *
     * @param playerID the ID of the player responding to the Ping message.
     * @param lobbyID  the ID of the lobby the player is currently in.
     */
    public PongMessage(String playerID, String lobbyID) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    /**
     * Executes the server message by invoking the 'pong' method in the client's network
     * to send the Pong response to the server.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            network.pong(playerID, lobbyID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
