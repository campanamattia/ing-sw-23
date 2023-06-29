package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

/**
 * Represents a server message indicating the outcome of a player's login attempt.
 * This message is used to notify the client's view about the result of the login operation.
 */
public class OutcomeLoginMessage extends ServerMessage {
    private final String playerID;
    private final String lobbyID;

    /**
     * Constructs a new OutcomeLoginMessage with the specified player ID and lobby ID.
     *
     * @param playerID the ID of the player who attempted to log in.
     * @param lobbyID  the ID of the lobby where the player attempted to log in.
     */
    public OutcomeLoginMessage(String playerID, String lobbyID){
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to update the view with the outcome of the login operation.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.outcomeLogin(playerID, lobbyID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
