package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;
import Utils.Tile;

import java.rmi.RemoteException;
import java.util.List;

public class OutcomeSelectTilesMessage extends ServerMessage {
    private final List<Tile> tiles;

    public OutcomeSelectTilesMessage(List<Tile> tiles){
        this.tiles = tiles;
    }

    public void execute(View view){
        try {
            view.outcomeSelectTiles(this.tiles);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
