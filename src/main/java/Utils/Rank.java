package Utils;

import java.io.Serializable;

/**
 * The Rank class represents a player with an ID and score.
 * It is implemented as a record, providing immutability and default implementations of equals(), hashCode(), and toString().
 */
public class Rank implements Serializable, Cloneable {
    private  String playerID;
    private  int totalScore;
    private  int personalScore;
    private  int patternScore;
    private  int sharedScore;

    public Rank(String playerID, int totalScore, int personalScore, int patternScore, int sharedScore) {
        this.playerID = playerID;
        this.totalScore = totalScore;
        this.personalScore = personalScore;
        this.patternScore = patternScore;
        this.sharedScore = sharedScore;
    }

    public String getPlayerID() {
        return playerID;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public Rank clone() {
        try {
            Rank clone = (Rank) super.clone();
            clone.playerID = this.playerID;
            clone.totalScore = this.totalScore;
            clone.personalScore = this.personalScore;
            clone.patternScore = this.patternScore;
            clone.sharedScore = this.sharedScore;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString(){
        return "PlayerID: " + playerID  + "\n\tPersonalScore: " + personalScore + "\n\tPatternScore: " + patternScore + "\n\tSharedScore: " + sharedScore + "\n\tTotalScore: " + totalScore;
    }
}