package Exception.Player;
import Exception.PlayerException;
public class ColumnNotValidException extends PlayerException {
    public ColumnNotValidException(int n) {
        super("The column "+n+" doesn't exist or doesn't has enough space");
    }
}
