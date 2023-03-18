package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SameEightGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public SameEightGoal(int nPlayer) {
        assert nPlayer<=4;
        assert nPlayer>=2;

        this.accomplished = new ArrayList<>();
        this.scoringToken = new Stack<>();

        if(nPlayer==2) {
            scoringToken.push(4);
            scoringToken.push(8);
        } else if(nPlayer==3){
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        } else if(nPlayer==4){
            scoringToken.push(2);
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }
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
        int countGreen=0, countBlue=0, countAzure=0, countYellow=0, countWhite=0, countPink=0;
        for (int i=5; i>=0; i--) {
            for(int j=4; j>=0; j--){
            }
        }
        return 2;

    }
}
