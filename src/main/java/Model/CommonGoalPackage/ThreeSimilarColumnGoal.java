package Model.CommonGoalPackage;

import Model.Player;
import Model.CommonGoal;
import Model.Shelf;

import java.util.List;
import java.util.Stack;

public class ThreeSimilarColumnGoal implements CommonGoal {


    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public ThreeSimilarColumnGoal(List<Player> accomplished, Stack<Integer> scoringToken) {
        this.accomplished = accomplished;
        this.scoringToken = scoringToken;
        this.description = "Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti. Colonne diverse possono avere combinazioni diverse di tipi di tessere.";
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
