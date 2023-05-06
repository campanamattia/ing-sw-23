package Utils.MockObjects;

import Utils.Tile;

public class MockPlayer {
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
}
