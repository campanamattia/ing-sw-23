package Server.Model.Player;

import Enumeration.Color;
import Exception.Player.ColumnNotValidException;
import Utils.Tile;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private final Tile[][] myShelf;

    /**
     * Constructs a new Shelf object with a default size of 6 rows and 5 columns.
     */
    public Shelf() {
        this.myShelf = new Tile[6][5];
    }

    /**
     * Inserts tiles into the specified column on the shelf.
     * The tiles are inserted from top to bottom until the column is full.
     *
     * @param n     The column index to insert the tiles into.
     * @param tiles The list of tiles to insert.
     * @throws ColumnNotValidException if the specified column is not valid or is already full.
     */
    public void insert(int n, List<Tile> tiles) throws ColumnNotValidException {
        if (n < 0 || n > 4 || myShelf[tiles.size() - 1][n] != null) throw new ColumnNotValidException(n);
        for (int i = 5; i >= 0; i--) {
            if (myShelf[i][n] == null) myShelf[i][n] = tiles.remove(0);
            if (tiles.isEmpty()) break;
        }
    }

    /**
     * Checks if the shelf is full.
     *
     * @return true if the shelf is full, false otherwise.
     */
    public boolean full() {
        for (int i = 0; i < 5; i++)
            if (this.myShelf[0][i] == null) return false;
        return true;
    }

    /**
     * Retrieves the tile at the specified position on the shelf.
     *
     * @param i The row index of the tile.
     * @param j The column index of the tile.
     * @return The tile at the specified position, or null if no tile is present.
     */
    public Tile getTile(int i, int j) {
        return myShelf[i][j];
    }

    /**
     * Retrieves the two-dimensional array representation of the shelf.
     *
     * @return The tile array representing the shelf.
     */
    public Tile[][] getMyShelf() {
        return myShelf;
    }

    /**
     * Calculates the score for the player's shelf based on tile groupings.
     *
     * @return The total score for the shelf.
     */
    public int checkEndGame() {
        boolean[][] visited = new boolean[numberRows()][numberColumns()];
        List<Integer> scores = new ArrayList<>();

        for (int i = 0; i < numberRows(); i++) {
            for (int j = 0; j < numberColumns(); j++) {
                if (this.myShelf[i][j] != null && !visited[i][j]) {
                    scores.add(getGroupScore(i, j, visited, this.myShelf[i][j].color()));
                }
            }
        }

        return scores.stream().mapToInt(score -> switch (score) {
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 5;
            default -> score >= 6 ? 8 : 0;
        }).sum();
    }

    private int getGroupScore(int row, int column, boolean[][] visited, Color color) {
        if (row < 0 || row >= numberRows() || column < 0 || column >= numberColumns() || myShelf[row][column] == null || visited[row][column]) {
            return 0;
        }

        if (myShelf[row][column].color() == color) {
            visited[row][column] = true;
            return 1
                    + getGroupScore(row + 1, column, visited, color)
                    + getGroupScore(row - 1, column, visited, color)
                    + getGroupScore(row, column + 1, visited, color)
                    + getGroupScore(row, column - 1, visited, color);
        }

        return 0;
    }

    /**
     * Returns the number of rows in the shelf.
     *
     * @return The number of rows in the shelf.
     */
    public int numberRows() {
        return myShelf.length;
    }

    /**
     * Returns the number of columns in the shelf.
     *
     * @return The number of columns in the shelf.
     */
    public int numberColumns() {
        return myShelf[0].length;
    }

    /**
     * Places a tile at the specified position on the shelf.
     * For testing purposes only.
     *
     * @param tile   The tile to place.
     * @param row    The row index of the position.
     * @param column The column index of the position.
     */
    public void placeTile(Tile tile, int row, int column) {
        myShelf[row][column] = tile;
    }

    public int maxTiles() {
        int max = 0;
        for(int i = 0; i < numberColumns(); i++){
            for (int j = numberRows()-1; j >= 0; j--) {
                if (myShelf[j][i] == null)
                    continue;
                if (j > max)
                    max = j;
                break;
            }
            if (max == numberRows())
                break;
        }
        return max;
    }
}
