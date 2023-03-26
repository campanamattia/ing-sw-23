package Exception.Board;

import Model.Coordinates;
import Exception.BoardException;


public class NullTileException extends BoardException {
    public NullTileException(Coordinates cd) {
        super("The selected tile <"+Integer.toString(cd.getX())+","+Integer.toString(cd.getY())+"> is null");
    }
}
