package Exception;

/**
 The {@code PlayerException} class is an abstract class that serves as the base class for player-related exceptions.
 It extends the {@link Exception} class and provides a constructor to set the error message.
 */
public abstract class PlayerException extends Exception{

    /**
     Constructs a new {@code PlayerException} with the specified error message.
     @param message the error message
     */
    public PlayerException(String message) {
        super(message);
    }
}