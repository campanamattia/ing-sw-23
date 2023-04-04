package Server.Model.CommonGoalPackage;

import Server.Exception.CommonGoal.NullPlayerException;
import Server.Model.*;
import com.google.gson.JsonObject;

import java.util.*;

public class DiagonalGoal extends CommonGoal {

    private final String description;
    private final int numDiagonal;

    public DiagonalGoal(List<Integer> tokenList, JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.numDiagonal = jsonObject.get("numDiagonal").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void check(Player player) throws NullPlayerException {
        if (player == null){
            throw new NullPlayerException();
        }
        Shelf shelf = player.getMyShelf();
        int countGroup = 0;

        // creating list for count the four different way to found diagonal
        List<Color> lSx1 = new ArrayList<>(), lSx2 = new ArrayList<>(), lDx1 = new ArrayList<>(), lDx2 = new ArrayList<>();

        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j <= 4; j++ ) {

                if (shelf.getTile(i,j) == null) {
                    continue;
                }

                if (i == j) {
                    lSx1.add(shelf.getTile(i,j).getTileColor());
                }
                if (i == j + 1) {
                    lSx2.add(shelf.getTile(i,j).getTileColor());
                }
                if (i + j == 4) {
                    lDx1.add(shelf.getTile(i,j).getTileColor());
                }
                if (i + j == 5) {
                    lDx2.add(shelf.getTile(i,j).getTileColor());
                }
            }
        }

        if (lSx1.stream().distinct().count() == 1 && lSx1.size() == 5) {
            countGroup++;
        }
        if (lSx2.stream().distinct().count() == 1 && lSx2.size() == 5) {
            countGroup++;
        }
        if (lDx1.stream().distinct().count() == 1 && lDx1.size() == 5) {
            countGroup++;
        }
        if (lDx2.stream().distinct().count() == 1 && lDx2.size() == 5) {
            countGroup++;
        }

        if (countGroup == numDiagonal) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
    }
}