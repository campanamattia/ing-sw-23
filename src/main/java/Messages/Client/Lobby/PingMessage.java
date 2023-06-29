package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

/**
 * Represents a client message used for sending a ping to the server's lobby.
 * It is sent by a client to check the connection status with the lobby.
 */
public class PingMessage extends ClientMessage {
    private final String lobbyID;

    /**
     * Constructs a PingMessage with the specified player ID and lobby ID.
     *
     * @param playerID the ID of the player sending the ping.
     * @param lobbyID  the ID of the lobby to ping.
     */
    public PingMessage(String playerID,String lobbyID){
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    /**
     * Executes the client message by invoking the appropriate method on the server to send the ping.
     *
     * @param socketHandler the SocketHandler used for communication with the server.
     */
    public void execute(SocketHandler socketHandler){
        try {
            ServerApp.lobby.ping(this.playerID,this.lobbyID);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
