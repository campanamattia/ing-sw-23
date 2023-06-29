package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

/**
 * Represents a server message indicating the start of a new turn in the game.
 * This message is used to notify the client's view about the current player's turn.
 */
public class NewTurnMessage extends ServerMessage {
    private final String currentPlayer;

    /**
     * Constructs a new NewTurnMessage with the specified current player.
     *
     * @param currentPlayer the ID or name of the current player.
     */
    public NewTurnMessage(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to update the view with the information about the new turn and the current player.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.newTurn(this.currentPlayer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
