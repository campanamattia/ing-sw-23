package Server.Model.CommonGoalPackage;

import Server.Model.Color;
import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;

import java.util.ArrayList;
import java.util.Stack;

public class GroupTwoGoal extends CommonGoal {

    public GroupTwoGoal(int nPlayer) {
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
        this.description = "Sei gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo (non necessariamente come mostrato in figura). Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.";
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
                    if (count >= 2) {
                        groups += count/2;
                        if (groups >= 6) {
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
