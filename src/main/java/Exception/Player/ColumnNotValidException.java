package Exception.Player;
import Exception.PlayerException;

/**
 The {@code ColumnNotValidException} class is an exception that is thrown when a column is not valid for a player's action.
 It extends the {@link PlayerException} class and provides information about the invalid column.
 */
public class ColumnNotValidException extends PlayerException {

    /**
     Constructs a new {@code ColumnNotValidException} with the specified column number.
     @param n the invalid column number
     */
    public ColumnNotValidException(int n) {
        super("The column "+n+" doesn't exist or doesn't have enough space");
    }
}
