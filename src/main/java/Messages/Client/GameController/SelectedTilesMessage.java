package Messages.Client.GameController;

import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;
import Utils.Coordinates;

import java.rmi.RemoteException;
import java.util.List;

public class SelectedTilesMessage extends ClientMessage {
    private final List<Coordinates> coordinates;

    public SelectedTilesMessage(String playerID, List<Coordinates> coordinates){
        this.playerID = playerID;
        this.coordinates = coordinates;
    }

    public void execute(SocketHandler socketHandler) {
        GameController gameController=  socketHandler.getGameController();
        try {
            gameController.selectTiles(this.playerID,this.coordinates);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.getMessage());
        }
    }
}
