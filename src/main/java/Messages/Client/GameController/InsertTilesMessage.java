package Messages.Client.GameController;

import Interface.Server.GameCommand;
import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents a client message used to insert tiles into the player's shelf.
 * It is sent by a client to the server to perform the tile insertion operation.
 */
public class InsertTilesMessage extends ClientMessage {
    private final List<Integer> sorted;
    private final int column;

    /**
     * Constructs an InsertTilesMessage with the specified player ID, sorted tile indices, and column number.
     *
     * @param playerID the ID of the player performing the tile insertion.
     * @param sorted   the list of sorted tile indices to be inserted.
     * @param column   the column number where the tiles will be inserted.
     */
    public InsertTilesMessage(String playerID, List<Integer> sorted, int column) {
        this.playerID = playerID;
        this.sorted = sorted;
        this.column = column;
    }

    /**
     * Executes the client message by invoking the appropriate method on the game controller to insert the tiles.
     *
     * @param socketHandler the SocketHandler used for communication with the server.
     */
    public void execute(SocketHandler socketHandler) {
        GameCommand gameController=  socketHandler.getGameController();
        try {
            gameController.insertTiles(this.playerID, this.sorted, this.column);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
