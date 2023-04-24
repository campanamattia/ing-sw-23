package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;
import Server.Model.Coordinates;

import java.util.List;

public class SelectedTiles extends ClientMessage {
    List<Coordinates> selectedCoordinates;

    public SelectedTiles(OperationType operationType, String playerID, List<Coordinates> selectedCoordinates) {
        this.operationType = operationType;
        this.playerID = playerID;
        this.selectedCoordinates = selectedCoordinates;
    }
}
