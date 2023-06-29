package Exception.Board;

import Exception.BoardException;

/**
 The {@code CantRefillBoardException} class is an exception that is thrown when the bag does not have enough tiles
 to refill the game board.
 It extends the {@link BoardException} class and provides a default constructor that sets a predefined error message.
 */
public class CantRefillBoardException extends BoardException {

    /**
     Constructs a new {@code CantRefillBoardException} with a predefined error message.
     The error message indicates that the bag does not have enough tiles.
     */
    public CantRefillBoardException() {
        super("The bag does not have enough tiles");
    }
}
