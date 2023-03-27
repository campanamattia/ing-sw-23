package Server.Exception.Board;

import Server.Exception.BoardException;

public class CantRefillBoardException extends BoardException {
    public CantRefillBoardException() {
        super("The bag does not have enough tiles");
    }
}
