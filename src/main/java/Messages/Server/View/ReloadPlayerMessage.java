package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class ReloadPlayerMessage extends ServerMessage {
    private final String reloadPlayer;

    public ReloadPlayerMessage(String reloadPlayer) {
        this.reloadPlayer = reloadPlayer;
    }

    @Override
    public void execute(View view) {
        try {
            view.reloadPlayer(this.reloadPlayer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
