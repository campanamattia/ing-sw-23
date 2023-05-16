package Messages.Server;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;
import Utils.Cell;
import Utils.MockObjects.MockModel;
import Utils.Tile;

import java.util.HashMap;

public class AllGameMessage extends ServerMessage {
    MockModel mockModel;

    public AllGameMessage(MockModel mockModel) {
        this.mockModel = mockModel;
    }

    @Override
    public void execute(View view) {
        view.allGame(this.mockModel);
    }
}
