package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.ArrayList;
import java.util.Stack;

public class DiagonalGoal extends CommonGoal {

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
        } else {
            scoringToken.push(2);
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }
        this.description = "Five tiles of the same type forming a diagonal.";
    }

    @Override
    public void check(Player player) {
        Shelf shelf = player.getShelf();
        if ((shelf.getTile(0, 0).getTileColor() == shelf.getTile(1, 1).getTileColor()) &&
                (shelf.getTile(0, 0).getTileColor() == shelf.getTile(2, 2).getTileColor()) &&
                (shelf.getTile(0, 0).getTileColor() == shelf.getTile(3, 3).getTileColor()) &&
                (shelf.getTile(0, 0).getTileColor() == shelf.getTile(4, 4).getTileColor())) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        } else if ((shelf.getTile(1, 0).getTileColor() == shelf.getTile(2, 1).getTileColor()) &&
                    (shelf.getTile(1, 0).getTileColor() == shelf.getTile(3, 2).getTileColor()) &&
                    (shelf.getTile(1, 0).getTileColor() == shelf.getTile(4, 3).getTileColor()) &&
                    (shelf.getTile(1, 0).getTileColor() == shelf.getTile(5, 4).getTileColor())) {
                accomplished.add(player.getID());
                player.updateScore(scoringToken.pop());
        } else if ((shelf.getTile(0, 4).getTileColor() == shelf.getTile(1, 3).getTileColor()) &&
                    (shelf.getTile(0, 4).getTileColor() == shelf.getTile(2, 2).getTileColor()) &&
                    (shelf.getTile(0, 4).getTileColor() == shelf.getTile(3, 1).getTileColor()) &&
                    (shelf.getTile(0, 4).getTileColor() == shelf.getTile(4, 0).getTileColor())) {
                accomplished.add(player.getID());
                player.updateScore(scoringToken.pop());
        } else if ((shelf.getTile(1, 4).getTileColor() == shelf.getTile(2, 3).getTileColor()) &&
                    (shelf.getTile(1, 4).getTileColor() == shelf.getTile(3, 2).getTileColor()) &&
                    (shelf.getTile(1, 4).getTileColor() == shelf.getTile(4, 1).getTileColor()) &&
                    (shelf.getTile(1, 4).getTileColor() == shelf.getTile(5, 0).getTileColor())) {
                accomplished.add(player.getID());
                player.updateScore(scoringToken.pop());
        }
    }
}
