package Server.Exception.Board;

import Server.Model.Coordinates;
import Server.Exception.BoardException;


public class NullTileException extends BoardException {
    public NullTileException(Coordinates cd) {
        super("The selected tile <"+ cd.getX() +","+ cd.getY() +"> is null");
    }
}