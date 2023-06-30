package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

/**
 * Represents a server message requesting the client's view to ask for the lobby size.
 * This message is used to prompt the client to input the desired lobby size.
 */
public class AskLobbySizeMessage extends ServerMessage {


    /**
     * Executes the server message by invoking the corresponding method in the client's view to ask for the lobby size.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.askLobbySize();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
