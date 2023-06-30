package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

/**
 * Represents a server message indicating an error occurred during the game.
 * This message is used to notify the client's view about the occurrence of an error.
 */
public class ErrorMessage extends ServerMessage {
    private final Exception error;

    /**
     * Constructs a new ErrorMessage with the specified error.
     *
     * @param error the Exception object representing the error that occurred.
     */
    public ErrorMessage(Exception error){
        this.error = error;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to handle the error and provide the necessary feedback or error handling.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.outcomeException(this.error);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
