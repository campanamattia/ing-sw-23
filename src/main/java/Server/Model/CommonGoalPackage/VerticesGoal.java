package Server.Model.CommonGoalPackage;

import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;

import java.util.ArrayList;
import java.util.Stack;

public class VerticesGoal extends CommonGoal {

    public VerticesGoal(int nPlayer) {
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
        this.description = "Quattro tessere dello stesso tipo ai quattro angoli della Libreria.";
    }


    @Override
    public void check(Player player) {
        Shelf shelf = player.getShelf();
        if (shelf.getTile(5,0) != null && shelf.getTile(5,4) != null &&
                shelf.getTile(0,0) != null && shelf.getTile(0,4) != null &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(0,4).getTileColor() &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(5,0).getTileColor() &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(5,4).getTileColor()) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
    }
}
