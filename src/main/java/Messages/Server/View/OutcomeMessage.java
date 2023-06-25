package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class OutcomeMessage extends ServerMessage {
    private final String outcome;

    public OutcomeMessage(String outcome) {
        this.outcome = outcome;
    }

    @Override
    public void execute(View view) {
        try {
            view.outcomeMessage(outcome);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
