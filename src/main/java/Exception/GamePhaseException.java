package Exception;

/**
 The {@code GamePhaseException} class is an abstract class that serves as the base class for game phase-related exceptions.
 It extends the {@link Exception} class and provides a default constructor.
 */
public abstract class GamePhaseException extends Exception{

    /**
     Constructs a new {@code GamePhaseException} with no specified detail message.
     */
    public GamePhaseException(){
        super();
    }
}
