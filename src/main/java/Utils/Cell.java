package Utils;

import java.io.Serializable;

/**
 * The Cell class represents a cell containing a Tile and its status.
 * It implements the Serializable and Cloneable interfaces.
 */
public class Cell implements Serializable, Cloneable {
    private Tile tile;
    private boolean status;

    /**
     * Constructs a new Cell object with a default Tile and status.
     * The default Tile is created with a null color and the status is set to false.
     */
    public Cell() {
        this.tile = new Tile(null);
        this.status = false;
    }

    /**
     * Constructs a new Cell object with the specified Tile and status.
     *
     * @param tile   The Tile object to be associated with the cell.
     * @param status The status of the cell.
     */
    public Cell(Tile tile, boolean status) {
        this.tile = tile;
        this.status = status;
    }

    /**
     * Returns the Tile associated with the cell.
     *
     * @return The Tile object associated with the cell.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Sets the Tile associated with the cell.
     *
     * @param tile The Tile object to be associated with the cell.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Returns the status of the cell.
     *
     * @return The status of the cell.
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Sets the status of the cell.
     *
     * @param status The status of the cell.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Creates and returns a deep copy of the Cell object.
     *
     * @return A deep copy of the Cell object.
     */
    @Override
    public Cell clone() {
        try {
            Cell clone = (Cell) super.clone();
            clone.tile = this.tile.clone();
            clone.status = this.status;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Tile removeTile() {
        Tile tile = this.tile;
        this.tile = null;
        return tile;
    }
}
