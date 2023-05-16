package Messages.Server;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;

public class AddedPlayerMessage extends ServerMessage {
    private final String playerID;

    public AddedPlayerMessage(String playerID) {
        this.playerID = playerID;
    }

    public void execute(View view){
        view.addedPlayer(playerID);
    }
}
