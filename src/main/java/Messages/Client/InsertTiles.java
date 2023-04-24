package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;

import java.util.List;
public class InsertTiles extends ClientMessage {
    List<Integer> sorted;
    int column;

    public InsertTiles(OperationType operationType, String playerID, List<Integer> sorted, int column) {
        this.operationType = operationType;
        this.playerID = playerID;
        this.sorted = sorted;
        this.column = column;
    }

    public List<Integer> getSorted() {
        return sorted;
    }

    public int getColumn() {
        return column;
    }
}
