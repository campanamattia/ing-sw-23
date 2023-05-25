package Utils;

import java.io.Serializable;

public class Cell implements Serializable, Cloneable {
    private Tile tile;
    private boolean status;

    public Cell() {
        this.tile = new Tile(null);
        this.status = false;
    }

    public Cell(Tile tile) {
        this.tile = tile;
        this.status = true;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean usable){
        this.status = usable;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
