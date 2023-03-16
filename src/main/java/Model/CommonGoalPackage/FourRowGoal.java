package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.List;
import java.util.Stack;

public class FourRowGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public FourRowGoal(List<Player> accomplished, Stack<Integer> scoringToken) {
        this.accomplished = accomplished;
        this.scoringToken = scoringToken;
        this.description = "Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti. Righe diverse possono avere combinazioni diverse di tipi di tessere.";
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
