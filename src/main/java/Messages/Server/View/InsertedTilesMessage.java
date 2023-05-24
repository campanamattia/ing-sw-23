package Messages.Server.View;

import Messages.ServerMessage;
import Client.View.*;

import java.rmi.RemoteException;

public class InsertedTilesMessage extends ServerMessage {
    public InsertedTilesMessage() {

    }

    public void execute(View view) {
        try {
            view.outcomeInsertTiles(true);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
