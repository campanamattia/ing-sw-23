package Utils;

import Enumeration.Color;

import java.io.Serializable;

/**
 * The Tile class represents a colored tile used in the game.
 * It is implemented as record, so it's immutable and implements the Cloneable and Serializable interfaces.
 */
public record Tile(Color color) implements Cloneable, Serializable {

    /**
     * Creates and returns a copy of this Tile object.
     *
     * @return A new Tile object that is a copy of this instance.
     */
    @Override
    public Tile clone() {
        try {
            return (Tile) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
