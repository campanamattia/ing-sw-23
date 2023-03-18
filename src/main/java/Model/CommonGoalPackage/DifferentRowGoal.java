package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DifferentRowGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private String description;

    public DifferentRowGoal(int nPlayer) { //costruttore da rivedere
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
        this.description = "Due righe formate ciascuna da 5 diversi tipi di tessere.";
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

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int check(Shelf shelf) {
        int countRow=0;
        for(int i=5; i>=0; i--){
            int countTile=0;
            for (int j=4; j>=0; j--){
                if(shelf.getTile(i,j) != null &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i,j+1).getTileColor()) {
                    countTile++;
                }
                if (countTile==4) {
                    countRow++;
                }
                if (countRow==2){
                    accomplished.add(shelf.getPlayer());
                    return scoringToken.pop();
                }
            }
        }
        return 0;
    }
}
