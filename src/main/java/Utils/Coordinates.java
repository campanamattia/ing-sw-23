package Utils;

import java.io.Serializable;

/**
 Represents the coordinates of a point on a two-dimensional grid.
 */
public record Coordinates(int x, int y) implements Serializable {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinates other) {
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }
}
