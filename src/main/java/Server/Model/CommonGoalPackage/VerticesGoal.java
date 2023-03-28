package Server.Model.CommonGoalPackage;

import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class VerticesGoal extends CommonGoal {

    private final String description;

    public VerticesGoal(List<Integer> tokenList, JsonObject jsonObject) {
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

        if (shelf.getTile(5,0) != null && shelf.getTile(5,4) != null &&
                shelf.getTile(0,0) != null && shelf.getTile(0,4) != null &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(0,4).getTileColor() &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(5,0).getTileColor() &&
                shelf.getTile(0,0).getTileColor() == shelf.getTile(5,4).getTileColor()) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
    }
}
