package Server.Model.CommonGoalPackage;

import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SameNGoal extends CommonGoal {

    private final String description;
    private final int numEquals;

    public SameNGoal(List<Integer> tokenList, JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.numEquals = jsonObject.get("numEquals").getAsInt();

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
        int countGreen = 0, countBlue = 0, countCyan = 0, countYellow = 0, countWhite = 0, countPink = 0;
        for (int i = 5; i >= 0; i--) {
            for(int j = 0; j <= 4; j--){

                if (shelf.getTile(i,j) == null) {
                    continue;
                }

                switch (shelf.getTile(i,j).getTileColor()){
                    case PINK -> {
                        countPink++;
                        if (countPink == numEquals) {
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case CYAN -> {
                        countCyan++;
                        if (countCyan == numEquals) {
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case BLUE -> {
                        countBlue++;
                        if (countBlue == numEquals) {
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case GREEN -> {
                        countGreen++;
                        if (countGreen == numEquals) {
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case WHITE -> {
                        countWhite++;
                        if (countWhite == numEquals) {
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case YELLOW -> {
                        countYellow++;
                        if (countYellow == numEquals) {
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                }
            }
        }
    }
}
