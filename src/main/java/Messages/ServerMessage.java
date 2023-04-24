package Messages;

import Enumeration.MessageType;

;

public abstract class ServerMessage {
    protected MessageType messageType;

    public ServerMessage(){
        this.messageType = null;
    }
    public MessageType getMessageType() {
        return messageType;
    }
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
