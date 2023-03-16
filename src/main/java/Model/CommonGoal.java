package Model;

import java.util.*;

public abstract class CommonGoal {
    private List<String> accomplished;
    private Stack<Integer> scoringToken;
    private String description;

    public abstract int check (Shelf s);

    public Stack<Integer> getScoringToken() {
        return scoringToken;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAccomplished () {
        return this.accomplished;
    }

    public void setAccomplished(List<String> accomplished) {
        this.accomplished = accomplished;
    }

    public void setScoringToken(Stack<Integer> scoringToken) {
        this.scoringToken = scoringToken;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
