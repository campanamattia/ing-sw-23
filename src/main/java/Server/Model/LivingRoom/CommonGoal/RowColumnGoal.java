package Server.Model.LivingRoom.CommonGoal;

import Exception.CommonGoal.NullPlayerException;
import Server.Model.Player.Player;
import Server.Model.Player.Shelf;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import Enumeration.Color;
import java.util.*;

/**
 * The RowColumnGoal class represents a goal where players must create row or column of tile with different pattern.
 * It extends the CommonGoal class and contains a number of column, the number of row, and the max number of tile different for column/row.
 */
public class RowColumnGoal extends CommonGoal {

    /**
     * The number of required column.
     */
    private final int numColumn;

    /**
     * The number of required row.
     */
    private final int numRow;

    /**
     * The max number of different color tiles in each row/column.
     */
    private final int maxDifferent;

    /**
     Create a new RowColumnGoal instance with the provided token list and JSON object.
     @param tokenList The list of scoring tokens earnable by players, based on how many players are in the game.
     @param jsonObject The JSON object containing the properties for this objective.
     It must have "enum", "description", "numColumn", "numRow", and "maxDifferent" properties.
     @throws NullPointerException if the jsonObject parameter is null.
     */
    public RowColumnGoal (List<Integer> tokenList, @NotNull JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.numColumn = jsonObject.get("numColumn").getAsInt();
        this.numRow = jsonObject.get("numRow").getAsInt();
        this.maxDifferent = jsonObject.get("maxDifferent").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }


    /**
     Checks if a player has achieved the RowColumnGoal and updates his score accordingly.
     If the player has achieved the goal, their ID is saved in the "accomplished" attribute.
     @param player The player to check for RowColumnGoal achievement.
     @throws NullPlayerException if the player parameter is null.
     */
    public void check (Player player) throws NullPlayerException {
        if (player == null || this.accomplished.contains(player.getPlayerID())) {
            throw new NullPlayerException();
        }

        Shelf shelf = player.getMyShelf();
        int countColumn = 0, countRow = 0;

        // check column goal
        if (numColumn != -1) {
            for (int j = 0; j < shelf.numberColumns(); j++) {
                List<Color> colorColumn = new ArrayList<>();
                for (int i = 0; i < shelf.numberRows(); i++) {
                    if (shelf.getTile(i, j) != null) {
                        colorColumn.add(shelf.getTile(i, j).color());
                    } else {
                        break;
                    }
                }

                if (colorColumn.size() == shelf.numberRows()) {
                    if (maxDifferent == -1 && colorColumn.stream().distinct().count() == 1) {
                        countColumn++;
                    } else if (maxDifferent != -1 && colorColumn.stream().distinct().count() <= maxDifferent) {
                        countColumn++;
                    }
                }
            }

            if (countColumn >= numColumn) {
                accomplished.add(player.getPlayerID());
                player.updateScore(scoringToken.pop());
                return;
            }
        }

        // check rowGoal
        if (numRow != -1) {
            for (int i = 0; i < shelf.numberRows(); i++) {
                List<Color> colorRow = new ArrayList<>();
                for (int j = 0; j < shelf.numberColumns(); j++) {
                    if (shelf.getTile(i, j) != null) {
                        colorRow.add(shelf.getTile(i, j).color());
                    } else {
                        break;
                    }
                }

                if (colorRow.size() == shelf.numberColumns()) {
                    if (maxDifferent == -1 && colorRow.stream().distinct().count() == 1) {
                        countRow++;
                    } else if (maxDifferent != -1 && colorRow.stream().distinct().count() <= maxDifferent) {
                        countRow++;
                    }
                }
            }

            if (countRow >= numRow) {
                accomplished.add(player.getPlayerID());
                player.updateScore(scoringToken.pop());
            }
        }
    }
}
