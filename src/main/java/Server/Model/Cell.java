package Server.Model;


import com.google.gson.annotations.Expose;

public class Cell {
    @Expose
    private Tile tile;
    @Expose
    private boolean status;

    public Cell() {
        this.tile = new Tile(null);
        this.status = false;
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
