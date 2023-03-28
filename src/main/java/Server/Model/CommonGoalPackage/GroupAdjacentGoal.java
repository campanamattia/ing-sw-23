package Server.Model.CommonGoalPackage;
import Server.Model.*;
import com.google.gson.JsonObject;
import java.util.*;

public class GroupAdjacentGoal extends CommonGoal {
    private final int numGroup;
    private final int numAdjacent;
    private final String description;

    public GroupAdjacentGoal(List<Integer> tokenList, JsonObject jsonObject) {

        this.description = jsonObject.get("description").getAsString();
        this.enumeration = jsonObject.get("enum").getAsInt();
        this.numGroup = jsonObject.get("numGroup").getAsInt();
        this.numAdjacent = jsonObject.get("numAdjacent").getAsInt();

        this.accomplished = new ArrayList<>();

        this.scoringToken = new Stack<>();
        scoringToken.addAll(tokenList);
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public void check(Player player) {
        Shelf shelf = player.getMyShelf();
        boolean[][] visited = new boolean[6][5];
        int groups = 0;
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                if (!visited[i][j]) {
                    if (shelf.getTile(i, j) == null) {
                        visited[i][j] = true;
                        continue;
                    }
                    Color color = shelf.getTile(i, j).getTileColor();
                    int count = countSameAdjacent(shelf, visited, i, j, color);
                    if (count >= numAdjacent) {
                        groups ++;
                        if (groups >= numGroup) {
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                }
            }
        }
    }

    private static int countSameAdjacent(Shelf shelf, boolean[][] visited, int row, int column, Color color) {
        if (row < 0 || row >= 6 || column >= 5 || visited[row][column] || shelf.getTile(row, column).getTileColor() != color) {
            return 0;
        }
        visited[row][column] = true;
        int count = 1;
        count += countSameAdjacent(shelf, visited, row - 1, column, color);
        count += countSameAdjacent(shelf, visited, row + 1, column, color);
        count += countSameAdjacent(shelf, visited, row, column - 1, color);
        count += countSameAdjacent(shelf, visited, row, column + 1, color);
        return count;
    }
}