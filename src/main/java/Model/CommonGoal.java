package Model;

import java.util.*;

public interface CommonGoal {
    List<Player> getAccomplished();

    Stack<Integer> getScoringToken ();

    String getDescription ();

    void setAccomplished(List<Player> accomplished);

    void setScoringToken(Stack<Integer> scoringToken);

    int check(Shelf shelf);
}
