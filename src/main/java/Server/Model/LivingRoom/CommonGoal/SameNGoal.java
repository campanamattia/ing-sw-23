package Server.Model.LivingRoom.CommonGoal;

import Exception.CommonGoal.NullPlayerException;
import Server.Model.Player.Player;
import Server.Model.Player.Shelf;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The SameNGoal class represents a goal where a player must have a certain number of tiles
 * of the same color on their shelf to achieve the goal.
 * It extends the CommonGoal class and contains a number of same color tile.
 */
public class SameNGoal extends CommonGoal {

    /**
     * The tiles' number the same color required to accomplish the SameNGoal.
     */
    private final int numEquals;

    /**
     Create a new SameNGoal instance with the provided token list and JSON object.
     @param tokenList The list of scoring tokens earnable by players, based on how many players are in the game.
     @param jsonObject The JSON object containing the properties for this objective.
     It must have "enum", "description", and "numEquals" properties.
     @throws NullPointerException if the jsonObject parameter is null.
     */
    public SameNGoal(List<Integer> tokenList, @NotNull JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.numEquals = jsonObject.get("numEquals").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }


    /**
     Checks if a player has achieved the SameNGoal and updates his score accordingly.
     If the player has achieved the goal, their ID is saved in the "accomplished" attribute.
     @param player The player to check for SameNGoal achievement.
     @throws NullPlayerException if the player parameter is null.
     */
    @Override
    public void check(Player player) throws NullPlayerException {
        if (player == null || this.accomplished.contains(player.getPlayerID())){
            throw new NullPlayerException();
        }

        Shelf shelf = player.getMyShelf();
        int countGreen = 0, countBlue = 0, countCyan = 0, countYellow = 0, countWhite = 0, countPink = 0;
        for (int i = 0; i < shelf.numberRows(); i++) {
            for(int j = 0; j < shelf.numberColumns(); j++){

                if (shelf.getTile(i,j) == null) {
                    continue;
                }

                switch (shelf.getTile(i,j).color()){
                    case PINK -> {
                        countPink++;
                        if (countPink == numEquals) {
                            accomplished.add(player.getPlayerID());
                            player.updateScore(scoringToken.pop());
                            return;
                        }
                    }
                    case CYAN -> {
                        countCyan++;
                        if (countCyan == numEquals) {
                            accomplished.add(player.getPlayerID());
                            player.updateScore(scoringToken.pop());
                            return;
                        }
                    }
                    case BLUE -> {
                        countBlue++;
                        if (countBlue == numEquals) {
                            accomplished.add(player.getPlayerID());
                            player.updateScore(scoringToken.pop());
                            return;
                        }
                    }
                    case GREEN -> {
                        countGreen++;
                        if (countGreen == numEquals) {
                            accomplished.add(player.getPlayerID());
                            player.updateScore(scoringToken.pop());
                            return;
                        }
                    }
                    case WHITE -> {
                        countWhite++;
                        if (countWhite == numEquals) {
                            accomplished.add(player.getPlayerID());
                            player.updateScore(scoringToken.pop());
                            return;
                        }
                    }
                    case YELLOW -> {
                        countYellow++;
                        if (countYellow == numEquals) {
                            accomplished.add(player.getPlayerID());
                            player.updateScore(scoringToken.pop());
                            return;
                        }
                    }
                }
            }
        }
    }
}
