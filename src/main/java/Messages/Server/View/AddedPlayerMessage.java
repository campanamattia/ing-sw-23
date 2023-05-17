package Messages.Server.View;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class AddedPlayerMessage extends ServerMessage {
    private final String playerID;
    private final String lobbyID;

    public AddedPlayerMessage(String playerID, String lobbyID) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    public void execute(View view){
        try {
            view.outcomeLogin(playerID, lobbyID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
