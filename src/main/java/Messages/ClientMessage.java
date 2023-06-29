package Messages;

import Server.Network.Client.SocketHandler;

import java.io.Serializable;

/**
 * An abstract base class representing a message from a client to the server.
 * Subclasses of ClientMessage define specific types of client messages.
 */
public abstract class ClientMessage implements Serializable {
    protected String playerID;

    /**
     * Executes the client message on the specified socket handler.
     *
     * @param socket the socket handler on which the client message should be executed.
     */
    public abstract void execute(SocketHandler socket);
}
