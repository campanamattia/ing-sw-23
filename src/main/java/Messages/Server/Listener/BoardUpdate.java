package Messages.Server.Listener;

import Client.View.View;
import Messages.ServerMessage;
import Utils.MockObjects.MockBoard;

import java.rmi.RemoteException;

public class BoardUpdate extends ServerMessage {

    private final MockBoard mockBoard;

    public BoardUpdate(MockBoard mockBoard) {
        this.mockBoard = mockBoard;
    }

    public void execute(View view){
        try {
            view.updateBoard(this.mockBoard);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
