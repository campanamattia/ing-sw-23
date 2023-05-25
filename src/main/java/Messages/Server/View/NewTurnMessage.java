package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class NewTurnMessage extends ServerMessage {
    private final String currentPlayer;

    public NewTurnMessage(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute(View view) {
        try {
            view.newTurn(this.currentPlayer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
