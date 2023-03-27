package Server.Model.CommonGoalPackage;

import Server.Model.*;
import com.google.gson.JsonObject;
import java.util.*;

public class CrossGoal extends CommonGoal {
    public CrossGoal(List<Integer> tokenList, JsonObject jsonObject) {


    }

    public void check(Player player) {
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
