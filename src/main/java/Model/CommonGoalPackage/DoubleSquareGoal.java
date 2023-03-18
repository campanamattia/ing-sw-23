package Model.CommonGoalPackage;
import Model.CommonGoal;
import Model.Player;
import Model.Shelf;
import Model.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DoubleSquareGoal implements CommonGoal {
    private List<Player> accomplished;
    private Stack<Integer> scoringToken;
    private final String description;

    public DoubleSquareGoal(int nPlayer) {
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
        this.description = "Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2. Le tessere dei due gruppi devono essere dello stesso tipo.";
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
        List<Tile> squareTile = new ArrayList<>();
        for (int i=5; i>=1; i--){
            for (int j=0; j<=3; j++){
                if (shelf.getTile(i,j) != null &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i-1,j).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i,j+1).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i-1,j+1).getTileColor()){
                    squareTile.add(shelf.getTile(i,j));
                }
            }
        }
        for (Tile tile : squareTile) {

        }
    }
}
