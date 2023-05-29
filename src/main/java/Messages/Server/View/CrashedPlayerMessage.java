package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class CrashedPlayerMessage extends ServerMessage {
    private final String crashedPlayer;

    public CrashedPlayerMessage(String crashedPlayer) {
        this.crashedPlayer = crashedPlayer;
    }

    @Override
    public void execute(View view) {
        try {
            view.crashedPlayer(crashedPlayer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
