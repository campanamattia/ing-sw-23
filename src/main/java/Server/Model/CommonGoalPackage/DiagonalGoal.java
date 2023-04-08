package Server.Model.CommonGoalPackage;

import Exception.CommonGoal.NullPlayerException;
import Server.Model.*;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import Enumeration.Color;
import java.util.*;


/**
 * The DiagonalGoal class represents a goal where players must create groups of tiles in a cross shape on their shelf.
 * It extends the CommonGoal class and contains a number of required groups.
 */
public class DiagonalGoal extends CommonGoal {

    /**
     * The number of required diagonal.
     */
    private final int numDiagonal;


    /**
     Create a new DiagonalGoal instance with the provided token list and JSON object.
     @param tokenList The list of scoring tokens earnable by players, based on how many players are in the game.
     @param jsonObject The JSON object containing the properties for this objective.
     It must have "enum", "description", and "numDiagonal" properties.
     @throws NullPointerException if the jsonObject parameter is null.
     */
    public DiagonalGoal(List<Integer> tokenList, @NotNull JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.numDiagonal = jsonObject.get("numDiagonal").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }

    /**
     Checks if a player has achieved the DiagonalGoal and updates his score accordingly.
     If the player has achieved the goal, their ID is saved in the "accomplished" attribute.
     @param player The player to check for DiagonalGoal achievement.
     @throws NullPlayerException if the player parameter is null.
     */
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

        if (countGroup >= numDiagonal) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
    }
}