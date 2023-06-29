package Exception.Board;
import Exception.BoardException;

/**
 The {@code NoValidMoveException} class is an exception that is thrown when there are no valid moves available on the game board.
 It extends the {@link BoardException} class and provides a default constructor that sets a predefined error message.
 */
public class NoValidMoveException extends BoardException {

    /**
     Constructs a new {@code NoValidMoveException} with a predefined error message.
     The error message indicates that there are no valid moves available because the tiles are not removable.
     */
    public NoValidMoveException(String message){
        super(message);
    }
}