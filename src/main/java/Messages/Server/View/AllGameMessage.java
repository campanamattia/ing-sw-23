package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;
import Utils.MockObjects.MockModel;

import java.rmi.RemoteException;

public class AllGameMessage extends ServerMessage {
    private final MockModel mockModel;

    public AllGameMessage(MockModel mockModel) {
        this.mockModel = mockModel;
    }

    @Override
    public void execute(View view) {
        try {
            view.allGame(this.mockModel);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
