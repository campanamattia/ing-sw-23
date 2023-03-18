package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DiagonalGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public DiagonalGoal(int nPlayer) {
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
        this.description = "Cinque tessere dello stesso tipo che formano una diagonale.";
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
        if (shelf.getTile(0,0).getTileColor() == shelf.getTile(1,1).getTileColor() &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(2,2).getTileColor() &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(3,3).getTileColor() &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(4,4).getTileColor() ) {
            return scoringToken.pop();
        } else if (shelf.getTile(1,0).getTileColor() == shelf.getTile(2,1).getTileColor() &&
                shelf.getTile(1,0).getTileColor() == shelf.getTile(3,2).getTileColor() &&
                shelf.getTile(1,0).getTileColor() == shelf.getTile(4,3).getTileColor() &&
                shelf.getTile(1,0).getTileColor() == shelf.getTile(5,4).getTileColor()) {
            accomplished.add(shelf.getPlayer());
            return scoringToken.pop();
        }
        return 0;
    }
}
