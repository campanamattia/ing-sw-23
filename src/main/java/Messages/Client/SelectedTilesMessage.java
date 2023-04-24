package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;
import Utils.Coordinates;

import java.util.List;

public class SelectedTilesMessage extends ClientMessage {
    private List<Coordinates> coordinates;

    public SelectedTilesMessage(){
        super();
        this.coordinates = null;
    }

    public SelectedTilesMessage(String playerID, List<Coordinates> coordinates){
        this.operationType = OperationType.SELECTEDTILES;
        this.playerID = playerID;
        this.coordinates = coordinates;
    }


    public List<Coordinates> getCoordinates() {
        return this.coordinates;
    }
    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }
}
