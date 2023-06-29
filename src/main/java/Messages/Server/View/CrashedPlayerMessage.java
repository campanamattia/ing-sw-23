package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

/**
 * Represents a server message indicating that a player has crashed or disconnected from the game.
 * This message is used to notify the client's view about the crashed player.
 */
public class CrashedPlayerMessage extends ServerMessage {
    private final String crashedPlayer;

    /**
     * Constructs a new CrashedPlayerMessage with the specified crashed player's ID.
     *
     * @param crashedPlayer the ID of the crashed player.
     */
    public CrashedPlayerMessage(String crashedPlayer) {
        this.crashedPlayer = crashedPlayer;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to handle the crashed player event using the provided player's ID.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.crashedPlayer(crashedPlayer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
