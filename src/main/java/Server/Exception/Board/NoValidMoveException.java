package Server.Exception.Board;
import Server.Exception.BoardException;

import Server.Model.Coordinates;

public class NoValidMoveException extends BoardException {
    public NoValidMoveException(){
        super("Tiles are not removable");
    }
}