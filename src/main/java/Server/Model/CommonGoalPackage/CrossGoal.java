package Server.Model.CommonGoalPackage;

import Model.*;
import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CrossGoal extends CommonGoal {
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
        else {
            scoringToken.push(2);
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }

        this.description = "Five tiles of the same type forming an X.";
    }

    public void check(@NotNull Player player) {
        Shelf shelf = player.getShelf();
        for (int i = 4; i >= 1; i--) {
            for (int j = 1; j <= 3; j++) {
                if ((shelf.getTile(i, j) != null) &&
                        (shelf.getTile(i, j).getTileColor() == shelf.getTile(i - 1, j - 1).getTileColor()) &&
                        (shelf.getTile(i, j).getTileColor() == shelf.getTile(i - 1, j + 1).getTileColor()) &&
                        (shelf.getTile(i, j).getTileColor() == shelf.getTile(i + 1, j - 1).getTileColor()) &&
                        (shelf.getTile(i, j).getTileColor() == shelf.getTile(i + 1, j + 1).getTileColor())) {
                    accomplished.add(player.getID());
                    player.updateScore(scoringToken.pop());
                }
            }
        }
    }
}
