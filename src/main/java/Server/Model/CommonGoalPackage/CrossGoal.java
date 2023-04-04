package Server.Model.CommonGoalPackage;

import Server.Exception.CommonGoal.NullPlayerException;
import Server.Model.*;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CrossGoal extends CommonGoal {

    private final String description;
    private final int numGroup;

    public CrossGoal(List<Integer> tokenList, @NotNull JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.numGroup = jsonObject.get("numGroup").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }

    public String getDescription() {
        return description;
    }

    public void check(Player player) throws NullPlayerException {
        if (player == null) {
            throw new NullPlayerException();
        }
        Shelf shelf = player.getMyShelf();
        int countGroup = 0;
        for (int i = 4; i >= 1; i--) {
            for (int j = 1; j <= 3; j++) {
                if ((shelf.getTile(i, j) != null) &&
                        (shelf.getTile(i-1, j-1) != null) && (shelf.getTile(i-1, j+1) != null) &&
                        (shelf.getTile(i+1, j-1) != null) && (shelf.getTile(i+1, j+1) != null) &&
                        (shelf.getTile(i, j).getTileColor() == shelf.getTile(i - 1, j - 1).getTileColor()) &&
                        (shelf.getTile(i, j).getTileColor() == shelf.getTile(i - 1, j + 1).getTileColor()) &&
                        (shelf.getTile(i, j).getTileColor() == shelf.getTile(i + 1, j - 1).getTileColor()) &&
                        (shelf.getTile(i, j).getTileColor() == shelf.getTile(i + 1, j + 1).getTileColor())) {
                    countGroup++;
                }
            }
        }
        if (countGroup == numGroup) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
    }
}
