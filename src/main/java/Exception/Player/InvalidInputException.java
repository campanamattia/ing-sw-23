package Exception.Player;
import Exception.PlayerException;

/**
 The {@code InvalidInputException} class is an exception that is thrown when the input parameters provided by a player are invalid or non-conforming.
 It extends the {@link PlayerException} class and provides flexibility for specifying the error message.
 */
public class InvalidInputException extends PlayerException {

    /**
     Constructs a new {@code InvalidInputException} with a default error message.
     The default message indicates that the input parameters are non-conforming.
     */
    public InvalidInputException() {
        super("Non-conforming input parameters");
    }
}
