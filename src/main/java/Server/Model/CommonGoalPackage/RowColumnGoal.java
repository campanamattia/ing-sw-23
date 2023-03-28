package Server.Model.CommonGoalPackage;

import Server.Model.*;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RowColumnGoal extends CommonGoal {

    private final String description;
    private final int numColumn;
    private final int numRow;
    private final int maxDifferent;

    public RowColumnGoal (List<Integer> tokenList, JsonObject jsonObject) {
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.description = jsonObject.get("description").getAsString();
        this.numColumn = jsonObject.get("numColumn").getAsInt();
        this.numRow = jsonObject.get("numRow").getAsInt();
        this.maxDifferent = jsonObject.get("maxDifferent").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }

    public String getDescription() {
        return description;
    }

    public void check (Player player) {
        Shelf shelf = player.getMyShelf();
        int countColumn = 0, countRow = 0;
        // check column goal
        if (numColumn != -1) {
            for (int j = 0; j <= 4; j++) {
                List<Color> colorColumn = new ArrayList<>();
                for (int i = 5; i >= 0; i--) {
                    if (shelf.getTile(i, j) != null) {
                        colorColumn.add(shelf.getTile(i, j).getTileColor());
                    } else {
                        break;
                    }
                }


                if (colorColumn.size() == 6) {
                    if (maxDifferent == -1 && colorColumn.stream().distinct().count() == 1) {
                        countColumn++;
                    } else if (maxDifferent != -1 && colorColumn.stream().distinct().count() <= maxDifferent) {
                        countColumn++;
                    }
                }
            }

            if (countColumn == numColumn) {
                accomplished.add(player.getID());
                player.updateScore(scoringToken.pop());
            }
        }

        // check rowGoal
        if (numRow != -1) {
            for (int i = 5; i >= 0; i--) {
                List<Color> colorRow = new ArrayList<>();
                for (int j = 0; j <= 4; j++) {
                    if (shelf.getTile(i, j) != null) {
                        colorRow.add(shelf.getTile(i, j).getTileColor());
                    } else {
                        break;
                    }
                }

                if (colorRow.size() == 5) {
                    if (maxDifferent == -1 && colorRow.stream().distinct().count() == 1) {
                        countRow++;
                    } else if (maxDifferent != -1 && colorRow.stream().distinct().count() <= maxDifferent) {
                        countRow++;
                    }
                }
            }

            if (countRow == numRow) {
                accomplished.add(player.getID());
                player.updateScore(scoringToken.pop());
            }
        }
    }
}
