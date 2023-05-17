package Messages.Client.GameController;

import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;
import Utils.Coordinates;
import java.util.List;

public class SelectedTilesMessage extends ClientMessage {
    private final List<Coordinates> coordinates;

    public SelectedTilesMessage(String playerID, List<Coordinates> coordinates){
        this.playerID = playerID;
        this.coordinates = coordinates;
    }

    public void execute(SocketHandler socketHandler) {
        GameController gameController=  socketHandler.getGameController();
        gameController.selectTiles(this.playerID,this.coordinates);
    }
}
