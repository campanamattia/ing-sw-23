package Exception.Board;

import Utils.Coordinates;
import Exception.BoardException;


public class NullTileException extends BoardException {
    public NullTileException(Coordinates cd) {
        super("The selected tile <"+ cd.x() +","+ cd.y() +"> is null");
    }
}
