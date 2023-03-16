package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Shelf;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class GroupFourGoal extends CommonGoal {
    private List<String> accomplished;
    private Stack<Integer> scoringToken;
    private String description;

    public GroupFourGoal(List<String> accomplished, Stack<Integer> scoringToken, String description) {
        this.accomplished = accomplished;
        this.scoringToken = scoringToken;
        this.description = description;
    }

    @Override
    public int check(Shelf s) {
        return 0;
    }
}
