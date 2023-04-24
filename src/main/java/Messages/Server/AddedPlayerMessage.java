package Messages.Server;

import Enumeration.MessageType;
import Messages.ServerMessage;

public class AddedPlayerMessage extends ServerMessage {

    public AddedPlayerMessage() {
        this.messageType = MessageType.RETURN;
    }
}
