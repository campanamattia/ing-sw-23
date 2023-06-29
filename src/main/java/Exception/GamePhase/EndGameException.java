package Exception.GamePhase;
import Exception.GamePhaseException;


/**
 The {@code EndGameException} class is an exception that is thrown when the game reaches its end phase.
 It extends the {@link GamePhaseException} class and does not provide any additional functionality or information.
 */
public class EndGameException extends GamePhaseException {

    /**
     Constructs a new {@code EndGameException} with no error message.
     */
    public EndGameException() {
        super();
    }
}
