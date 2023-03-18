package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DifferentColumnGoal implements CommonGoal {

    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public DifferentColumnGoal(int nPlayer) { //costruttore da rivedere
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
        this.description = "Due colonne formate ciascuna da 6 diversi tipi di tessere.";
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
        int countColumn=0;
        for (int j=4; j>=0; j--) {
            int countTiles=0;
            for (int i=5; i>=0; i--) {
                if (shelf.getTile(i, j) != null &&
                        shelf.getTile(i,j).getTileColor() != shelf.getTile(i+1,j).getTileColor() ) {
                    countTiles++;
                }
            }
            if (countTiles==5) { countColumn++; }
            if (countColumn==2) {
                accomplished.add(shelf.getPlayer());
                return scoringToken.pop();
            }
        }
        return 0;
    }
}
