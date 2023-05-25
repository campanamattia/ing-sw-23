package Messages.Server.View;

import Messages.ServerMessage;
import Client.View.*;

import java.rmi.RemoteException;

public class OutcomeInsertTilesMessage extends ServerMessage {
    private final boolean outcome;

    public OutcomeInsertTilesMessage(boolean outcome) {
        this.outcome = outcome;
    }

    public void execute(View view) {
        try {
            view.outcomeInsertTiles(outcome);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
