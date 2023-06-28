package Exception.Board;
import Exception.BoardException;

public class NoValidMoveException extends BoardException {
    public NoValidMoveException(String message){
        super(message);
    }
}