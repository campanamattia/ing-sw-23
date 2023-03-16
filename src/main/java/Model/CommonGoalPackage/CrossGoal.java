package Model.CommonGoalPackage;

import Model.*;

import java.util.List;
import java.util.Stack;

public class CrossGoal extends CommonGoal {

    private List<String> accomplished;
    private Stack<Integer> scoringToken;
    private String description;

    public CrossGoal(List<String> accomplished, Stack<Integer> scoringToken, String description) {
        this.accomplished = accomplished;
        this.scoringToken = scoringToken;
        this.description = description;
    }

    @Override
    public int check(Shelf s) {
        return 0;
    }
}
