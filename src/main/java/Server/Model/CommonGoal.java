package Server.Model;

import java.util.*;

public abstract class CommonGoal {

    protected List<String> accomplished;
    protected Stack<Integer> scoringToken;
    protected String description;

    public List<String> getAccomplished() {
        return accomplished;
    }

    public void setAccomplished(List<String> accomplished) {
        this.accomplished = accomplished;
    }

    public Stack<Integer> getScoringToken() {
        return scoringToken;
    }

    public void setScoringToken(Stack<Integer> scoringToken) {
        this.scoringToken = scoringToken;
    }

    public String getDescription() {
        return description;
    }

    public abstract void check(Player player);
}
