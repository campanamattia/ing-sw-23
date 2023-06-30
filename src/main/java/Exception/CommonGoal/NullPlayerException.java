package Exception.CommonGoal;

/**
 The {@code NullPlayerException} class is an exception that is thrown when a null player is encountered in the context of a common goal.
 It extends the {@link Exception} class and provides a constructor that sets a corresponding error message.
 */
public class NullPlayerException extends Exception {

    /**
     Constructs a new {@code NullPlayerException} with a default error message indicating that the player is null.
     */
    public NullPlayerException () {
        super ("The player is null");
    }
}
