package Server.Model;

import java.util.*;

public abstract class CommonGoal {

    protected List<String> accomplished;
    protected Stack<Integer> scoringToken;
    protected int enumeration;

    public List<String> getAccomplished() {
        return accomplished;
    }

    public Stack<Integer> getScoringToken() {
        return scoringToken;
    }

    public abstract void check(Player player);
}
