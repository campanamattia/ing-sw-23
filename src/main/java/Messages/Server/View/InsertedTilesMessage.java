package Messages.Server.View;

import Enumeration.MessageType;
import Messages.ServerMessage;
import Client.View.*;
import Utils.Coordinates;

import java.util.List;

public class InsertedTilesMessage extends ServerMessage {
    private final String playerID;
    private final List<Integer> sorted;
    private final int column;
    public InsertedTilesMessage(String playerID, List<Integer> sorted, int column) {
        this.playerID = playerID;
        this.sorted = sorted;
        this.column = column;
        this.messageType = MessageType.RETURN;
    }

    public void execute(View view) {
        view.outcomeInsertTiles(playerID, sorted, column);
    }
}
