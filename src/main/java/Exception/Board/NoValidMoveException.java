package Exception.Board;
import Exception.BoardException;

import Model.Coordinates;

public class NoValidMoveException extends BoardException {
    public NoValidMoveException(Coordinates cd){
        super("The tile <"+Integer.toString(cd.getX())+","+Integer.toString(cd.getY())+"> is not removable");
    }
}