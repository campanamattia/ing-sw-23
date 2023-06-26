package Utils.MockObjects;

import Utils.Cell;
import java.io.Serializable;

/**
 * The MockBoard class represents a mock board object.
 * It implements the Serializable and Cloneable interfaces.
 */
public class MockBoard implements Serializable, Cloneable{
    /**
     * The board attribute represents the board of the game.
     */
    private Cell[][] board;
    /**
     * The lastRound tells if is the last round of the game.
     */
    private boolean lastRound;

    /**
     * It returns the board of the game.
     * @return the board of the game.
     */
    public Cell[][] getBoard() {
        return board;
    }

    /**
     * It sets the board of the game.
     * @param board the board of the game.
     */
    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    /**
     * It returns if is the last round of the game.
     * @return true if is the last round of the game.
     */
    public boolean isLastRound() {
        return lastRound;
    }

    /**
     * It sets if is the last round of the game.
     * @param lastRound true if is the last round of the game.
     */
    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }

    /**
     * It returns a clone of the MockBoard object.
     * @return a clone of the MockBoard object.
     */
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
