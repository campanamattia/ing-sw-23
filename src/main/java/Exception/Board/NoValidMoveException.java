package Exception.Board;
import Exception.BoardException;

public class NoValidMoveException extends BoardException {
    public NoValidMoveException(){
        super("Tiles are not removable");
    }
}