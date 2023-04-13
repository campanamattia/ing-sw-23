package Exception.Player;
import Exception.PlayerException;
public class ColumnNotValidException extends PlayerException {
    public ColumnNotValidException(int n) {
        super("column "+ n +" does not have enough space for these tiles");
    }
}
