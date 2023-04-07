package Server.Model.CommonGoalPackage;

import Server.Exception.CommonGoal.NullPlayerException;
import Server.Model.*;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * The CrossGoal class represents a goal where players must create groups of tiles in a cross shape on their shelf.
 * It extends the CommonGoal class and contains a number of required groups.
 */
public class CrossGoal extends CommonGoal {

    /**
     * The number of required Cross.
     */
    private final int numGroup;

    /**
     Create a new CrossGoal with the provided token list and JSON object.
     @param tokenList The list of scoring tokens earnable by players, based on how many players are in the game.
     @param jsonObject The JSON object containing the properties for this goal.
     It must have "enum", "description", and "numGroup" properties.
     @throws NullPointerException if the jsonObject parameter is null.
     */
    public CrossGoal(List<Integer> tokenList, @NotNull JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.numGroup = jsonObject.get("numGroup").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }


    /**
     This method checks if a player has achieved the CrossGoal and updates his score accordingly.
     If the player has achieved the objective, their ID is saved in the "accomplished" attribute.
     @param player The player to check for CrossGoal achievement.
     @throws NullPlayerException if the player parameter is null.
     */
    @Override
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
        if (countGroup >= numGroup) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
    }
}
