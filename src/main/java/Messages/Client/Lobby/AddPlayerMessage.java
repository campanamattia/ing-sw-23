package Messages.Client.Lobby;


import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

/**
 * Represents a client message used to add a player to a lobby.
 * It is sent by a client to join a specific lobby identified by the lobby ID.
 */
public class AddPlayerMessage extends ClientMessage {
    private final String lobbyID;

    /**
     * Constructs an AddPlayerMessage with the specified player ID and lobby ID.
     *
     * @param playerID the ID of the player to be added.
     * @param lobbyID  the ID of the lobby to join.
     */
    public AddPlayerMessage(String playerID, String lobbyID) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    /**
     * Executes the client message by invoking the appropriate method on the server to add the player to the lobby.
     *
     * @param socketHandler the SocketHandler used for communication with the server.
     */
    public void execute(SocketHandler socketHandler){
        try {
            ServerApp.lobby.login(this.playerID,this.lobbyID, socketHandler, socketHandler);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
