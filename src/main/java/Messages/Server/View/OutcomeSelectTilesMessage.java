package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;
import Utils.Tile;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents a server message indicating the outcome of a tile selection operation in the game.
 * This message is used to notify the client's view about the of selected tiles and choose the order.
 */
public class OutcomeSelectTilesMessage extends ServerMessage {
    private final List<Tile> tiles;

    /**
     * Constructs a new OutcomeSelectTilesMessage with the specified list of tiles.
     *
     * @param tiles the list of tiles that were selected.
     */
    public OutcomeSelectTilesMessage(List<Tile> tiles){
        this.tiles = tiles;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to handle the outcome of the tile selection operation.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    public void execute(View view){
        try {
            view.outcomeSelectTiles(this.tiles);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
