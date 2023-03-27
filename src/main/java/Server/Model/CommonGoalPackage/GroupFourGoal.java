package Server.Model.CommonGoalPackage;

import Server.Model.Color;
import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;

import java.util.*;

public class GroupFourGoal extends CommonGoal {
    public GroupFourGoal(int nPlayer) {
        assert nPlayer <= 4;
        assert nPlayer >= 2;

        this.accomplished = new ArrayList<>();
        this.scoringToken = new Stack<>();

        if (nPlayer == 2) {
            scoringToken.push(4);
            scoringToken.push(8);
        } else if (nPlayer == 3) {
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        } else if (nPlayer == 4) {
            scoringToken.push(2);
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }
        this.description = "Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2. Le tessere dei due gruppi devono essere dello stesso tipo.";
    }

    @Override
    public void check(Player player) {
        Shelf shelf = player.getShelf();
        boolean[][] visited = new boolean[6][5];
        int groups = 0;
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                if (!visited[i][j]) {
                    if (shelf.getTile(i,j) == null)
                        continue;
                    Color color = shelf.getTile(i,j).getTileColor();
                    int count = countSameAdjacent(shelf, visited, i, j, color);
                    if (count >= 4) {
                        groups += count/4;
                        if (groups >= 4) {
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                }
            }
        }
    }

    private static int countSameAdjacent (Shelf shelf, boolean[][] visited, int row, int column, Color color) {
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
