package Exception.Board;

import Exception.BoardException;

public class CantRefillBoardException extends BoardException {
    public CantRefillBoardException() {
        super("The bag does not have enough tiles");
    }
}
