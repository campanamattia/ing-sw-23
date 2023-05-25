package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class OutcomeLoginMessage extends ServerMessage {
    private final String playerID;
    private final String lobbyID;

    public OutcomeLoginMessage(String playerID, String lobbyID){
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute(View view) {
        try {
            view.outcomeLogin(playerID, lobbyID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
