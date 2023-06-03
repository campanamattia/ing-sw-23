package Utils.MockObjects;

import Utils.Tile;

import java.io.Serializable;

public class MockPlayer implements Serializable, Cloneable {
    private String playerID;
    private Tile[][] personalGoal;
    private Tile[][] shelf;
    private int score;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public Tile[][] getPersonalGoal() {
        return personalGoal;
    }

    public void setPersonalGoal(Tile[][] personalGoal) {
        this.personalGoal = personalGoal;
    }

    public Tile[][] getShelf() {
        return shelf;
    }

    public void setShelf(Tile[][] shelf) {
        this.shelf = shelf;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MockPlayer player)
            return playerID.equals(player.playerID);
        return false;
    }
}
