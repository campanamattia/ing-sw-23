package Server.Model.LivingRoom.CommonGoal;

import Exception.CommonGoal.NullPlayerException;
import Server.Model.Player.Player;
import Server.Model.Player.Shelf;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import Enumeration.Color;
import java.util.*;

/**
 * The DoubleSquare class represents a goal where players must create groups of tiles in a cross shape on their shelf.
 * It extends the CommonGoal class and contains a number of required groups, and the dimension of the square.
 */
public class SquareGoal extends CommonGoal {

    /**
     * The side 's dimension of square for this SquareGoal.
     */
    private final int dimSquare;

    /**
     * The number of required groups for this SquareGoal.
     */
    private final int numGroup;

    /**
     Create a new SquareGoal instance with the provided token list and JSON object.
     @param tokenList The list of scoring tokens earnable by players, based on how many players are in the game.
     @param jsonObject The JSON object containing the properties for this objective.
     It must have "enum", "description", "dimSquare", and "numGroup" properties.
     @throws NullPointerException if the jsonObject parameter is null.
     */
    public SquareGoal(List<Integer> tokenList, @NotNull JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.dimSquare = jsonObject.get("dimSquare").getAsInt();
        this.numGroup = jsonObject.get("numGroup").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }


    /**
     This method checks if a player has achieved the SquareGoal and updates his score accordingly.
     If the player has achieved the goal, their ID is saved in the "accomplished" attribute.
     @param player The player to check for SquareGoal achievement.
     @throws NullPlayerException if the player parameter is null.
     */
    @Override
    public void check(Player player) throws NullPlayerException {
        if (player == null) {
            throw new NullPlayerException();
        }

        int countGroup = 0;
        Shelf shelf = player.getMyShelf();
        for (int i = dimSquare - 1; i < shelf.numberRows(); i++) {
            for (int j = 0; j <= shelf.numberColumns() - dimSquare; j++) {
                if (shelf.getTile(i,j) == null) {
                    continue;
                }
                if(checkSquare(shelf, i, j, dimSquare, shelf.getTile(i,j).color())) {
                    if (checkEdgesOfSquare(shelf, i, j, dimSquare, shelf.getTile(i,j).color())) {
                        countGroup++;
                    }
                }

            }
        }
        if (countGroup >= numGroup) {
            accomplished.add(player.getPlayerID());
            player.updateScore(scoringToken.pop());
        }
    }

    /**
     * Checks if there is a square of size dimSquare formed by tiles of the same color,
     * starting from row and column on the specified shelf.
     * @param shelf The shelf to check
     * @param row The row to start from
     * @param column The column to start from
     * @param dimSquare The size of the square
     * @param color The color of the tiles
     * @return true if a square of tiles of the same color is found, false otherwise
     */
    private boolean checkSquare (Shelf shelf, int row, int column, int dimSquare, Color color) {
        int count = 0;
        for (int i = row; i > row - dimSquare; i--) {
            for (int j = column; j < column + dimSquare; j++) {
                if (shelf.getTile(i,j) != null && shelf.getTile(i,j).color() == color) {
                    count++;
                }
            }
        }
        return count == dimSquare * dimSquare;
    }

    /**
     * Checks if the tiles that are located at the edges of the square of tiles of size dimSquare,
     * starting from row and column on the specified shelf, are all of a different color than the square.
     * @param shelf The shelf to check
     * @param row The row to start from
     * @param column The column to start from
     * @param dimSquare The size of the square
     * @param color The color of the tiles in the square
     * @return True if the tiles at the edges of the square are all of a different color, false otherwise
     */
    private boolean checkEdgesOfSquare (Shelf shelf, int row, int column, int dimSquare, Color color) {
        boolean bool = true;

        for (int i = row; i >= row - dimSquare + 1; i--) {
            try {
                if (shelf.getTile(i, column + dimSquare).color() == color) {
                    bool = false;
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored){
            }

            if (!bool) {
                return false;
            }

            try {
                if (shelf.getTile(i, column - 1).color() == color) {
                    bool = false;
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            }
            if (!bool) {
                return false;
            }
        }

        for (int j = column; j <= column + dimSquare + 1; j++) {
            try {
                if (shelf.getTile(row + 1, j).color() == color) {
                    bool = false;
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            }

            if (!bool) {
                return false;
            }

            try {
                if (shelf.getTile(row - dimSquare, j).color() == color) {
                    bool = false;
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            }

            if (!bool) {
                return false;
            }
        }
        return true;
    }
}
