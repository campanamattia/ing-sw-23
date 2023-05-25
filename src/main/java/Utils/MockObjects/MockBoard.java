package Utils.MockObjects;

import Utils.Cell;
import java.io.Serializable;

public class MockBoard implements Serializable, Cloneable{
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

    @Override
    public MockBoard clone() {
        try {
            MockBoard clone = (MockBoard) super.clone();

            clone.board = board.clone();
            clone.lastRound = lastRound;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
