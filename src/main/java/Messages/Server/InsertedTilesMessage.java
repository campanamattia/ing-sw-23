package Messages.Server;

import Enumeration.MessageType;
import Messages.ServerMessage;

public class InsertedTilesMessage extends ServerMessage {
    public InsertedTilesMessage() {
        this.messageType = MessageType.RETURN;
    }
}
