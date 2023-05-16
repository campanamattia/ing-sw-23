package Messages.Server;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;

public class AddedPlayerMessage extends ServerMessage {

    public AddedPlayerMessage() {
        this.messageType = MessageType.RETURN;
    }

    public void execute(View view, String playerID){
        view.addedPlayer(playerID);
    }
}
