package Messages;

import Client.View.View;

import java.io.Serializable;

/**
 * An abstract base class representing a message from the server to the client.
 * Subclasses of ServerMessage define specific types of server messages.
 */
public abstract class ServerMessage implements Serializable {

    /**
     * Executes the server message on the specified view.
     *
     * @param view the view on which the server message should be executed.
     */
    public abstract void execute(View view);
}