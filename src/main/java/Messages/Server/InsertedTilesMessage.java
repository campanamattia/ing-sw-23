package Messages.Server;

import Enumeration.MessageType;
import Messages.ServerMessage;
import Client.View.*;
import Utils.Coordinates;

import java.util.List;

public class InsertedTilesMessage extends ServerMessage {
    public InsertedTilesMessage() {
        this.messageType = MessageType.RETURN;
    }

    public void execute(View view, String playerID, List<Coordinates> sorted, int column) {
        view.outcomeInsertTiles(playerID, sorted, column);
    }
}
