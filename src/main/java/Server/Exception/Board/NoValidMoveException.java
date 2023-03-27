package Server.Exception.Board;
import Server.Exception.BoardException;

import Server.Model.Coordinates;

public class NoValidMoveException extends BoardException {
    public NoValidMoveException(Coordinates cd){
        super("The tile <"+ cd.getX() +","+ cd.getY() +"> is not removable");
    }
}