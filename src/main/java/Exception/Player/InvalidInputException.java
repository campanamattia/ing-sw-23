package Exception.Player;
import Exception.PlayerException;

public class InvalidInputException extends PlayerException {
    public InvalidInputException() {
        super("Non-conforming input parameters");
    }

    public InvalidInputException(String string) {
        super(string);
    }
}
