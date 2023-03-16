package Model;

import java.util.*;

public interface CommonGoal {
    public List<Player> getAccomplished();

    public Stack<Integer> getScoringToken ();

    public String getDescription ();

    public void setAccomplished(List<Player> accomplished);

    public void setScoringToken(Stack<Integer> scoringToken);

    public int check(Shelf shelf);
}
