package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Represents a server message requesting the client's view to ask for player information.
 * This message is used to provide the client with information about the players in the lobby.
 */
public class AskPlayerInfoMessage extends ServerMessage {
    private final List<Map<String, String>> lobbyInfo;

    /**
     * Constructs a new AskPlayerInfoMessage with the specified lobby information.
     *
     * @param lobbyInfo the list of player information in the lobby.
     */
    public AskPlayerInfoMessage(List<Map<String, String>> lobbyInfo) {
        this.lobbyInfo = lobbyInfo;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to ask for player information using the provided lobby information.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.askPlayerInfo(this.lobbyInfo);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
