package Model.CommonGoalPackage;

import Model.CommonGoal;
import Model.Player;
import Model.Shelf;

import java.util.ArrayList;
import java.util.Stack;

public class DifferentColumnGoal extends CommonGoal {
    public DifferentColumnGoal(int nPlayer) {
        assert nPlayer <= 4;
        assert nPlayer >= 2;

        this.accomplished = new ArrayList<>();
        this.scoringToken = new Stack<>();

        if (nPlayer == 2) {
            scoringToken.push(4);
            scoringToken.push(8);
        } else if (nPlayer == 3) {
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        } else {
            scoringToken.push(2);
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }
        this.description = "Two columns each formed by 6 different types of tiles";
    }
    @Override
    public void check(Player player) {
        Shelf shelf = player.getShelf();
        int countColumn = 0;
        for (int j = 0; j < 5; j++) {
            int countTiles = 0;
            for (int i = 5; i >= 0; i--) {
                if (shelf.getTile(i, j).getTileColor() != shelf.getTile(i + 1, j).getTileColor()) {
                    countTiles++;
                } else continue;

                if (countTiles == 5) {
                    countColumn++;
                }
                if (countColumn == 2) {
                    accomplished.add(player.getID());
                    player.updateScore(scoringToken.pop());
                }
            }
        }
    }
}