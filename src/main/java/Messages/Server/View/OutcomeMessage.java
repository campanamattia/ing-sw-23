package Messages.Server.View;

import Client.View.View;
import Enumeration.GameWarning;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class OutcomeMessage extends ServerMessage {
    private final GameWarning warning;

    public OutcomeMessage(GameWarning warning) {
        this.warning = warning;
    }

    @Override
    public void execute(View view) {
        try {
            view.outcomeMessage(warning);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
