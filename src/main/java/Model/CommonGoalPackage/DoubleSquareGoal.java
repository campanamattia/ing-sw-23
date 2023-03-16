package Model.CommonGoalPackage;
import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.List;
import java.util.Stack;

public class DoubleSquareGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public DoubleSquareGoal(List<Player> accomplished, Stack<Integer> scoringToken) {
        this.accomplished = accomplished;
        this.scoringToken = scoringToken;
        this.description = "Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2. Le tessere dei due gruppi devono essere dello stesso tipo.";
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
