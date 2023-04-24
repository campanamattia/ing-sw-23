package Messages.Server;

import Enumeration.MessageType;
import Messages.ServerMessage;
import Utils.Tile;

import java.util.List;

public class SelectedTilesMessage extends ServerMessage {
    private List<Tile> tiles;

    public SelectedTilesMessage(){
        super();
        this.tiles = null;
    }

    public SelectedTilesMessage(List<Tile> tiles){
        this.messageType = MessageType.RETURN;
        this.tiles = tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
