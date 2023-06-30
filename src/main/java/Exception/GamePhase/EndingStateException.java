package Exception.GamePhase;

import Exception.GamePhaseException;

/**
 The {@code EndingStateException} class is an exception that is thrown when the game is in an ending state.
 It extends the {@link GamePhaseException} class and does not provide any additional functionality or information.
 */
public class EndingStateException extends GamePhaseException {

    /**
     Constructs a new {@code EndingStateException} with no error message.
     */
    public EndingStateException() {
        super();
    }
}
