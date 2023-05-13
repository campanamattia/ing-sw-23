package Server.Model.LivingRoom.CommonGoal;

import Exception.CommonGoal.NullPlayerException;
import Server.Model.Player.Player;
import Server.Model.Player.Shelf;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.Math.min;

/**
 * The Staircase class represents a goal where players must create a staircase on their shelf.
 * It extends the CommonGoal class.
 */
public class StaircaseGoal extends CommonGoal {

    /**
     Create a new StaircaseGoal instance with the provided token list and JSON object.
     @param tokenList The list of scoring tokens earnable by players, based on how many players are in the game.
     @param jsonObject The JSON object containing the properties for this objective.
     It must have "enum", and "description" properties.
     @throws NullPointerException if the jsonObject parameter is null.
     */
    public StaircaseGoal(List<Integer> tokenList, @NotNull JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }

    /**
     Checks if a player has achieved the StaircaseGoal and updates his score accordingly.
     If the player has achieved the goal, their ID is saved in the "accomplished" attribute.
     @param player The player to check for StaircaseGoal achievement.
     @throws NullPlayerException if the player parameter is null.
     */
    @Override
    public void check(Player player) throws NullPlayerException {
        if(player == null) {
            throw new NullPlayerException();
        }

        Shelf shelf = player.getMyShelf();
        int countSx1 = 0, countSx2 = 0, countDx1 = 0, countDx2 = 0;
        int min = min(shelf.numberRows(),shelf.numberColumns());

        //limit case for the method LastTile()
        if (shelf.getTile(0, 0) != null) {
            countSx1++;
        }

        if (shelf.getTile(0, shelf.numberColumns() - 1) != null) {
            countDx1++;
        }

        for (int i = 1; i < shelf.numberRows(); i++) {
            for (int j = 0; j < shelf.numberColumns(); j++) {

                if (shelf.getTile(i, j) == null) {
                    continue;
                }

                if (i == j && lastTile(shelf, i , j)) {
                    countSx1++;
                }
                if (i == j + 1 && lastTile(shelf, i , j)) {
                    countSx2++;
                }
                if (i + j == min - 1 && lastTile(shelf, i , j)) {
                    countDx1++;
                }
                if (i + j == min && lastTile(shelf, i , j)) {
                    countDx2++;
                }
            }
        }

        if (countSx1 == min || countSx2 == min || countDx1 == min || countDx2 == min) {
            accomplished.add(player.getPlayerID());
            player.updateScore(scoringToken.pop());
        }
    }

    public static boolean lastTile(@NotNull Shelf shelf, int i, int j) {
        return shelf.getTile(i - 1, j) == null;
    }
}

