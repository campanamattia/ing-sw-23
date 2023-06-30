package Utils.MockObjects;

import Utils.Tile;

import java.io.Serializable;

/**
 * The MockPlayer class represents a mock player in the game.
 * It encapsulates information such as the player's ID, personal goals, shelf, score, and online status.
 * It implements the Serializable and Cloneable interfaces.
 */
public class MockPlayer implements Serializable, Cloneable {
    /**
     * The player's ID.
     */
    private String playerID;
    /**
     * The player's personal goals.
     */
    private Tile[][] personalGoal;
    /**
     * The player's shelf.
     */
    private Tile[][] shelf;
    /**
     * The player's score.
     */
    private int score;
    /**
     * The player's online status.
     */
    private boolean isOnline = true;

    /**
     * Return the player's ID.
     * @return The player's ID.
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Sets the player's ID.
     * @param playerID The player's ID.
     */
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    /**
     * Returns the player's personal goals.
     * @return The player's personal goals.
     */
    public Tile[][] getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Sets the player's personal goals.
     * @param personalGoal The player's personal goals.
     */
    public void setPersonalGoal(Tile[][] personalGoal) {
        this.personalGoal = personalGoal;
    }

    /**
     * Returns the player's shelf.
     * @return The player's shelf.
     */
    public Tile[][] getShelf() {
        return shelf;
    }

    /**
     * Sets the player's shelf.
     * @param shelf The player's shelf.
     */
    public void setShelf(Tile[][] shelf) {
        this.shelf = shelf;
    }

    /**
     * Returns the player's score.
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     * @param score The player's score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Creates and returns a deep copy of the MockPlayer object.
     *
     * @return A deep copy of the MockPlayer object.
     */
    @Override
    public MockPlayer clone() {
        try {
            MockPlayer clone = (MockPlayer) super.clone();

            clone.playerID = playerID;
            clone.personalGoal = personalGoal.clone();
            clone.shelf = shelf.clone();
            clone.score = score;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Compares the MockPlayer object with another object for equality.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MockPlayer player)
            return playerID.equals(player.playerID);
        return false;
    }

    /**
     * Set the player's status to online or offline.
     * @param online The player's status to onl
     */
    public void setOnline(boolean online) {
        isOnline = online;
    }

    /**
     * Returns the player's online status.
     * @return The player's online status.
     */
    public boolean isOnline() {
        return isOnline;
    }
}
