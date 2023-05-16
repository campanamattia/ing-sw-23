package Messages;

import Client.View.View;
import Enumeration.MessageType;

public abstract class ServerMessage {
    protected MessageType messageType;

    public ServerMessage(){
        this.messageType = null;
    }

    public abstract void execute(View view);
}