package Server.Model.CommonGoalPackage;

import Server.Model.*;
import com.google.gson.JsonObject;

import java.util.*;

public class DoubleSquareGoal extends CommonGoal {

    private final String description;
    private final int dimSquare;
    private final int numGroup;

    public DoubleSquareGoal(List<Integer> tokenList, JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.dimSquare = jsonObject.get("dimSquare").getAsInt();
        this.numGroup = jsonObject.get("numGroup").getAsInt();

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
        HashMap<Coordinates, Color> squareTile = new HashMap<>();
        Coordinates c;
        for (int i=5; i>=1; i--){
            for (int j=0; j<=3; j++){
                if (shelf.getTile(i,j) != null &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i-1,j).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i,j+1).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i-1,j+1).getTileColor()){
                    c = new Coordinates(i,j);
                    squareTile.put(c,shelf.getTile(i,j).getTileColor());
                }
            }
        }
        for (Map.Entry<Coordinates, Color> entry : squareTile.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Color color = entry.getValue();
            for (Map.Entry<Coordinates, Color> entry2 : squareTile.entrySet()) {
                Coordinates coordinates2 = entry2.getKey();
                Color color2 = entry2.getValue();
                int absX = Math.abs(coordinates.getX() - coordinates2.getX());
                int absY = Math.abs(coordinates.getY() - coordinates2.getY());
                if ( !coordinates.equals(coordinates2) && (absY >= 2 || absX >= 2) && color == color2) {
                    accomplished.add(player.getID());
                    player.updateScore(scoringToken.pop());
                }
            }
        }
    }
}

