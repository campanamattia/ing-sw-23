package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Stack;

public class VerticesGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public VerticesGoal(List<Player> accomplished, Stack<Integer> scoringToken) {
        this.accomplished = accomplished;
        this.scoringToken = scoringToken;
        this.description = "Quattro tessere dello stesso tipo ai quattro angoli della Libreria.";
    }

    public List<Player> getAccomplished() {
        return this.accomplished;
    }

    public void setAccomplished(List<Player> accomplished) {
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

    @Override
    public int check(Shelf shelf) {
        return 0;
    }
}
