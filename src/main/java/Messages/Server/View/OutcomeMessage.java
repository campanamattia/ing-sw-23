package Messages.Server.View;

import Client.View.View;
import Enumeration.GameWarning;
import Messages.ServerMessage;

import java.rmi.RemoteException;

/**
 * Represents a server message indicating a specific outcome or warning in the game.
 * This message is used to notify the client's view about the outcome or warning.
 */
public class OutcomeMessage extends ServerMessage {
    private final GameWarning warning;

    /**
     * Constructs a new OutcomeMessage with the specified game warning.
     *
     * @param warning the game warning associated with the outcome.
     */
    public OutcomeMessage(GameWarning warning) {
        this.warning = warning;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to display the outcome or warning in the game.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.outcomeMessage(warning);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
