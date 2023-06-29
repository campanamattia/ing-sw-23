package Exception;

/**
 The {@code ChatException} class represents an exception that occurs when there is an issue with a chat operation.
 It extends the {@link Exception} class and provides a default constructor that sets the default error message to "Empty Body".
 */
public class ChatException extends Exception{

    /**
     Constructs a new {@code ChatException} with the default error message "Empty Body".
     */
    public ChatException() {
        super("Empty Body");
    }
}
