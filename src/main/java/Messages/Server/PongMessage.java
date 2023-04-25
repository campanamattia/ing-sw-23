package Messages.Server;

import Enumeration.MessageType;
import Messages.ServerMessage;

public class PongMessage extends ServerMessage {

    public PongMessage() {
        this.messageType = MessageType.PONG;
    }
}
