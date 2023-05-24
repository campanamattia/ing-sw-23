package Utils.MockObjects;

import Utils.Cell;
import java.io.Serializable;

public class MockBoard implements Serializable{
    private Cell[][] board;
    private boolean lastRound;

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public boolean isLastRound() {
        return lastRound;
    }

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }
}
