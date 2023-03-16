package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Shelf;

import java.util.List;
import java.util.Stack;

public class FourRowGoal extends CommonGoal {
    private List<String> accomplished;
    private Stack<Integer> scoringToken;
    private String description;

    public FourRowGoal (List<String> accomplished, Stack<Integer> scoringToken, String description) {
        this.accomplished = accomplished;
        this.scoringToken = scoringToken;
        this.description = description;
    }

    @Override
    public int check(Shelf s) {
        return 0;
    }
}
