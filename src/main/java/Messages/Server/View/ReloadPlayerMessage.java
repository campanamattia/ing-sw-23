package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

/**
 * Represents a server message indicating that a player needs to be reloaded in the game.
 * This message is used to notify the client's view to reload a specific player's information.
 */
public class ReloadPlayerMessage extends ServerMessage {
    private final String reloadPlayer;

    /**
     * Constructs a new ReloadPlayerMessage with the specified player ID.
     *
     * @param reloadPlayer the ID of the player to be reloaded.
     */
    public ReloadPlayerMessage(String reloadPlayer) {
        this.reloadPlayer = reloadPlayer;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to reload the specified player's information.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.reloadPlayer(this.reloadPlayer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
