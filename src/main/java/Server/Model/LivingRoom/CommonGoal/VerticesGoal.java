package Server.Model.LivingRoom.CommonGoal;

import Exception.CommonGoal.NullPlayerException;
import Server.Model.Player.Player;
import Server.Model.Player.Shelf;
import Utils.Tile;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The VerticesGoal class represents a goal where players must have a tile on the vertice of the shelf.
 * It extends the CommonGoal class.
 */
public class VerticesGoal extends CommonGoal {

    /**
     Create a new VerticesGoal instance with the provided token list and JSON object.
     @param tokenList The list of scoring tokens earnable by players, based on how many players are in the game.
     @param jsonObject The JSON object containing the properties for this objective.
     It must have "enum", and "description" properties.
     @throws NullPointerException if the jsonObject parameter is null.
     */
    public VerticesGoal(List<Integer> tokenList, @NotNull JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }

    /**
     Checks if a player has achieved the VerticesGoal and updates his score accordingly.
     If the player has achieved the goal, their ID is saved in the "accomplished" attribute.
     @param player The player to check for VerticesGoal achievement.
     @throws NullPlayerException if the player parameter is null.
     */
    @Override
    public void check(Player player) throws NullPlayerException {
        if (player == null || this.accomplished.contains(player.getPlayerID())) {
            throw new NullPlayerException();
        }
        Shelf shelf = player.getMyShelf();
        Tile topLeftTile = shelf.getTile(0,0);
        Tile topRightTile = shelf.getTile(0,4);
        Tile lowLeftTile = shelf.getTile(5,0);
        Tile lowRightTile = shelf.getTile(5,4);
        try {
            if (topLeftTile.color() == topRightTile.color() &&
                    topLeftTile.color() == lowLeftTile.color() &&
                    topLeftTile.color() == lowRightTile.color()) {
                accomplished.add(player.getPlayerID());
                player.updateScore(scoringToken.pop());
            }
        }
        catch (NullPointerException ignored) {
        }
    }
}
