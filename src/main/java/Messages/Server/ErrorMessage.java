package Messages.Server;

import Enumeration.MessageType;
import Messages.ServerMessage;

public class ErrorMessage extends ServerMessage {
    private String error;

    public ErrorMessage(){
        super();
        this.error = null;
    }

    public ErrorMessage(String error){
        this.messageType = MessageType.ERROR;
        this.error = error;
    }

    public String getError() {
        return error;
    }
    public void setError(String error){
        this.error = error;
    }
}
