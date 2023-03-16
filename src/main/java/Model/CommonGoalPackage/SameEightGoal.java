package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.List;
import java.util.Stack;

public class SameEightGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public SameEightGoal(List<Player> accomplished, Stack<Integer> scoringToken) {
        this.accomplished = accomplished;
        this.scoringToken = scoringToken;
        this.description = "Otto tessere dello stesso tipo. Non ci sono restrizioni sulla posizione di queste tessere. Cinque colonne di altezza crescente o decrescente: a partire dalla prima colonna a sinistra o a destra, ogni colonna successiva ";
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
