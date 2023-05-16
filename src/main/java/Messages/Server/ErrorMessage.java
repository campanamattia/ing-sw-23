package Messages.Server;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class ErrorMessage extends ServerMessage {
    private final Exception error;

    public ErrorMessage(Exception error){
        this.error = error;
    }

    @Override
    public void execute(View view) {
        try {
            view.outcomeException(this.error);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
