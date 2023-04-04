package Server.Model;

import Server.Exception.CommonGoal.NullPlayerException;

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

    public int getEnumeration() {
        return enumeration;
    }

    public abstract void check(Player player) throws NullPlayerException;
}
