package Exception;

/**
 The {@code BoardException} class is an abstract class that represents an exception related to the game board.
 It extends the {@link Exception} class and provides a constructor to set the error message.
 */
public abstract class BoardException  extends  Exception{

    /**
     Constructs a new {@code BoardException} with the specified error message.
     @param s the error message describing the exception
     */
    public BoardException(String s) {
        super(s);
    }
}
