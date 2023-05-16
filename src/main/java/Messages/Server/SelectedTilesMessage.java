package Messages.Server;

import Client.View.View;
import Messages.ServerMessage;
import Utils.Tile;
import java.util.List;

public class SelectedTilesMessage extends ServerMessage {
    private final List<Tile> tiles;
    private final String playerID;

    public SelectedTilesMessage(String playerID, List<Tile> tiles){
        this.playerID = playerID;
        this.tiles = tiles;
    }

    public void execute(View view){
        view.outcomeSelectTiles(this.playerID,this.tiles);
    }
}
