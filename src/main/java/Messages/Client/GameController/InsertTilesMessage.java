package Messages.Client.GameController;

import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;
import java.util.List;

public class InsertTilesMessage extends ClientMessage {
    private final List<Integer> sorted;
    private final int column;


    public InsertTilesMessage(String playerID, List<Integer> sorted, int column) {
        this.playerID = playerID;
        this.sorted = sorted;
        this.column = column;
    }

    public void execute(SocketHandler socketHandler) {
        GameController gameController=  socketHandler.getGameController();
        try {
            gameController.insertTiles(this.playerID, this.sorted, this.column);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
