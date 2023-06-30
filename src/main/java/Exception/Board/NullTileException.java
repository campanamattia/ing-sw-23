package Exception.Board;

import Utils.Coordinates;
import Exception.BoardException;

/**
 The {@code NullTileException} class is an exception that is thrown when a null tile is encountered on the game board.
 It extends the {@link BoardException} class and provides a constructor that takes the coordinates of the null tile and sets a corresponding error message.
 */
public class NullTileException extends BoardException {

    /**
     Constructs a new {@code NullTileException} with the coordinates of the null tile.
     The error message is generated using the provided coordinates to indicate that the selected tile at the specified coordinates is null.
     @param cd the coordinates of the null tile
     */
    public NullTileException(Coordinates cd) {
        super("The selected tile <"+ cd.x() +","+ cd.y() +"> is null");
    }
}
