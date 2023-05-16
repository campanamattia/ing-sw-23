package Messages.Server;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;

public class ErrorMessage extends ServerMessage {
    private Exception error;

    public ErrorMessage(){
        super();
        this.error = null;
    }

    public ErrorMessage(Exception error){
        this.messageType = MessageType.ERROR;
        this.error = error;
    }

    public Exception getError() {
        return error;
    }
    public void setError(Exception error){
        this.error = error;
    }

    @Override
    public void execute(View view) {
        view.outcomeException(this.error);
    }
}
