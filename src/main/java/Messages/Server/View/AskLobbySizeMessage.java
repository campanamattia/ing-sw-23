package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class AskLobbySizeMessage extends ServerMessage {

    @Override
    public void execute(View view) {
        try {
            view.askLobbySize();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
