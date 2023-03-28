package Server.Model.CommonGoalPackage;

import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StaircaseGoal extends CommonGoal {

    private final String description;

    public StaircaseGoal(List<Integer> tokenList, JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void check(Player player) {
        Shelf shelf = player.getMyShelf();
        int countSx1 = 0, countSx2 = 0, countDx1 = 0, countDx2 = 0;

        //limit case for the method LastTile()
        if (shelf.getTile(0, 0) != null) {
            countSx1++;
        }

        if (shelf.getTile(0, 4) != null) {
            countDx1++;
        }

        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j <= 4; j++) {

                if (shelf.getTile(i, j) == null) {
                    continue;
                }

                if (i == j && lastTile(shelf, i , j)) {
                    countSx1++;
                }
                if (i == j + 1 && lastTile(shelf, i , j)) {
                    countSx2++;
                }
                if (i + j == 4 && lastTile(shelf, i , j)) {
                    countDx1++;
                }
                if (i + j == 5 && lastTile(shelf, i , j)) {
                    countDx2++;
                }
            }
        }

        if (countSx1 == 5 || countSx2 == 5 || countDx1 == 5 || countDx2 ==5) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
    }

    public static boolean lastTile(Shelf shelf, int i, int j) {
        if (shelf.getTile(i-1,j) == null) {
            return true;
        } else {
            return false;
        }
    }
}

/*
Shelf shelf = player.getMyShelf();
        int countSx = 0, countDx =0;
        for (int i = 5; i >= 1; i--) {
            for (int j = 0; j < 5 && j <= i; j++) {
                if (shelf.getTile(i,j) != null) {
                    countSx++;
                }
            }
        }
        if ((countSx == 15 && shelf.getTile(0,0) == null && shelf.getTile(1,1) == null && shelf.getTile(2,2) == null &&
                shelf.getTile(3,3) == null && shelf.getTile(4,4) == null) ||
                (countSx == 20 && shelf.getTile(0,1) == null && shelf.getTile(1,2) == null && shelf.getTile(2,3) == null &&
                        shelf.getTile(3,4) == null)) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }

        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 5 && j >= i; j++) {
                if (shelf.getTile(i,j) != null) {
                    countDx++;
                }
            }
        }
        if ((countDx == 15 && shelf.getTile(4,0) == null && shelf.getTile(3,1) == null && shelf.getTile(2,2) == null &&
                shelf.getTile(1,3) == null && shelf.getTile(0,4) == null) ||
                (countDx == 20 && shelf.getTile(3,0) == null && shelf.getTile(2,1) == null && shelf.getTile(1,2) == null &&
                        shelf.getTile(0,3) == null)) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
 */
