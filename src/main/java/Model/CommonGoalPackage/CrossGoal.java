package Model.CommonGoalPackage;

import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CrossGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public CrossGoal(int nPlayer) {
        assert nPlayer>=2;
        assert nPlayer<=4;

        this.accomplished = new ArrayList<>();
        this.scoringToken = new Stack<>();
        if(nPlayer==2) {
            scoringToken.push(4);
            scoringToken.push(8);
        }
        else if(nPlayer==3){
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }
        else if(nPlayer==4){
            scoringToken.push(2);
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }

        this.description = "Cinque tessere dello stesso tipo che formano una X.";
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
        for (int i=4; i >= 1; i--){
            for (int j=3; j >= 1; j--){
                if (shelf.getTile(i, j) != null &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i-1,j-1).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i-1,j+1).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i+1,j-1).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i+1,j+1).getTileColor()){
                    accomplished.add(shelf.getPlayer());
                    return scoringToken.pop();
                }
            }
        }
        return 0;
    }
}
