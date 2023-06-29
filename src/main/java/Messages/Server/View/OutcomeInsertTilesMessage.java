package Messages.Server.View;

import Messages.ServerMessage;
import Client.View.*;

import java.rmi.RemoteException;

/**
 * Represents a server message indicating the outcome of the tile insertion operation.
 * This message is used to notify the client's view about the result of inserting tiles into a column.
 */
public class OutcomeInsertTilesMessage extends ServerMessage {
    private final boolean outcome;

    /**
     * Constructs a new OutcomeInsertTilesMessage with the specified outcome.
     *
     * @param outcome the outcome of the tile insertion operation (true for success, false for failure).
     */
    public OutcomeInsertTilesMessage(boolean outcome) {
        this.outcome = outcome;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to update the view with the outcome of the tile insertion operation.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    public void execute(View view) {
        try {
            view.outcomeInsertTiles(outcome);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
