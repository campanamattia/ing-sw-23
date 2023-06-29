package Server.Model.LivingRoom;

import Exception.Board.CantRefillBoardException;
import Exception.Board.NoValidMoveException;
import Exception.Board.NullTileException;
import Utils.Cell;
import Utils.Coordinates;
import Utils.Tile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.abs;


/**
 * Class Board, it contains all the method to for player's move in the game.
 */
public class Board {
    /**
     * The 2D array representing the board.
     */
    private final Cell[][] board;
    /**
     * The number of tiles taken from the board.
     */
    private final List<Coordinates> tilesTaken;

    @TestOnly
    public int tilesTakenNumber;
    /**
     * The size of the board.
     */
    private final int matrix_size;

    /**
     * Class constructor.
     *
     * @param board_json it contains the scheme of the board.
     * @param bag        bag of tiles created at the start of the game.
     */
    public Board(JsonObject board_json, Bag bag) {
        this.matrix_size = board_json.get("matrix.size").getAsInt();
        this.board = new Cell[matrix_size][matrix_size];
        this.tilesTaken = new ArrayList<>();
        for (int i = 0; i < matrix_size; i++) {
            for (int j = 0; j < matrix_size; j++) {
                this.board[i][j] = new Cell();
            }
        }
        int board_size = board_json.get("board.size").getAsInt();
        List<Integer> cell_value = getValueList(board_json.getAsJsonArray("cell.value"));
        List<Tile> toDeploy = bag.draw(board_size);
        int k = 0;
        for (int i = 0; i < matrix_size; i++) {
            for (int j = 0; j < matrix_size; j++) {
                if (cell_value.get(k) == 1) {
                    this.board[i][j].setStatus(true);
                    this.board[i][j].setTile(toDeploy.remove(0));
                } else {
                    this.board[i][j].setStatus(false);
                    this.board[i][j].setTile(null);
                }
                k++;
            }
        }
    }

    private static List<Integer> getValueList(JsonArray json) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= json.size(); i++)
            list.add(json.get(json.size() - i).getAsInt());
        return list;
    }

    /**
     * Check if the move selected by the player is playable.
     *
     * @param coordinates coordinates of the tiles that the player wants to take.
     * @throws NoValidMoveException exception thrown if the move isn't valid.
     * @throws NullTileException    exception thrown if the player wants to take a tile from an empty cell.
     */
    public void convalidateMove(@NotNull List<Coordinates> coordinates) throws NoValidMoveException, NullTileException {
        if (coordinates.isEmpty() || coordinates.size() > 3)
            throw new NoValidMoveException("The number of tiles is not valid.");

        if (hasDuplicates(coordinates))
            throw new NoValidMoveException("The tiles are not unique.");

        for (Coordinates(int x, int y) : coordinates) {
            if (!areValidCoordinates(new Coordinates(x, y)))
                throw new NoValidMoveException("The coordinates [" + x + "][" + y + "] are not valid.");

            if (board[x][y].getTile() == null || !board[x][y].getStatus())
                throw new NullTileException(new Coordinates(x, y));

            if (!oneSideFree(x, y))
                throw new NoValidMoveException("The tile [" + x + "][" + y + "] has not at least one side free.");
        }

        if (!areAligned(coordinates)) throw new NoValidMoveException("The tiles are not aligned.");
    }

    private boolean hasDuplicates(List<Coordinates> coordinates) {
        return coordinates.stream().distinct().count() != coordinates.size();
    }

    private boolean areAligned(List<Coordinates> coordinates) {
        List<Coordinates> list = new ArrayList<>(coordinates);
        if (list.size() == 1) return true;

        boolean isHorizontal = list.get(0).x() == list.get(1).x();

        if (isHorizontal) list.sort(Comparator.comparingInt(Coordinates::y));
        else list.sort(Comparator.comparingInt(Coordinates::x));

        return IntStream.range(1, list.size()).allMatch(i -> {
            Coordinates prev = list.get(i - 1);
            Coordinates current = list.get(i);
            return isHorizontal ? prev.x() == prev.x() && abs(prev.y() - current.y()) == 1 : prev.y() == current.y() && abs(prev.x() - current.x()) == 1;
        });
    }

    private boolean areValidCoordinates(Coordinates coordinates) {
        return coordinates.x() >= 0 && coordinates.x() < matrix_size && coordinates.y() >= 0 && coordinates.y() < matrix_size;
    }

    private boolean oneSideFree(int x, int y) {
        if (onEdge(x, y)) return true;
        return Stream.of(board[x - 1][y].getTile(), board[x + 1][y].getTile(), board[x][y - 1].getTile(), board[x][y + 1].getTile()).anyMatch(Objects::isNull);
    }

    private boolean onEdge(int x, int y) {
        return x - 1 < 0 || x + 1 >= matrix_size || y - 1 < 0 || y + 1 >= matrix_size;
    }

    /**
     * Remove the tiles taken off the board.
     *
     * @param coordinates coordinate of the tiles that
     * @return list of the taken tiles.
     */
    public List<Tile> getTiles(@SuppressWarnings("unused") List<Coordinates> coordinates) throws NullTileException {
        List<Tile> tiles = new ArrayList<>();
        for (Coordinates(int x, int y) : coordinates) {
            if (board[x][y].getTile() == null) throw new NullTileException(new Coordinates(x, y));
            tiles.add(board[x][y].removeTile());
            tilesTaken.add(new Coordinates(x, y));
        }
        return tiles;
    }

    /**
     * Deploy n tiles on the board in the empty spaces.
     *
     * @param bag it contains all the tiles left for the game.
     */
    public void refill(Bag bag) {
        ArrayList<Tile> toDeploy = bag.draw(tilesTaken.size());
        for (Coordinates(int x, int y) : tilesTaken) {
            board[x][y].setTile(toDeploy.remove(0));
        }
    }


    /**
     * Check if there isn't playable move left on the board.
     *
     * @param bag it contains all the tiles left for the game.
     * @throws CantRefillBoardException exception thrown if there aren't enough tiles in the bag to refill the board.
     */
    public void checkRefill(Bag bag) throws CantRefillBoardException {
        for (int i = 0; i < matrix_size; i++) {
            for (int j = 0; j < matrix_size; j++) {
                if (!board[i][j].getStatus()) continue;
                if (board[i][j].getTile() == null) continue;
                if (!allSideFree(new Coordinates(i, j))) return;
            }
        }

        if (tilesTaken.size() > bag.getLastTiles()) throw new CantRefillBoardException();

        refill(bag);
        tilesTaken.clear();
        this.tilesTakenNumber = 0;
    }

    private boolean allSideFree(Coordinates coordinates) {
         if (areValidCoordinates(new Coordinates(coordinates.x() - 1, coordinates.y())) && board[coordinates.x() - 1][coordinates.y()].getTile() != null)
             return false;
         if (areValidCoordinates(new Coordinates(coordinates.x() + 1, coordinates.y())) && board[coordinates.x() + 1][coordinates.y()].getTile() != null)
             return false;
         if (areValidCoordinates(new Coordinates(coordinates.x(), coordinates.y() - 1)) && board[coordinates.x()][coordinates.y() - 1].getTile() != null)
             return false;
        return !areValidCoordinates(new Coordinates(coordinates.x(), coordinates.y() + 1)) || board[coordinates.x()][coordinates.y() + 1].getTile() == null;
    }

    /**
     * It returns the 2D array of the board.
     */
    public Cell[][] getBoard() {
        return board;
    }


    @TestOnly
    public void setTilesTaken(int n) {
        tilesTakenNumber = n;
        for (int i = 0; i < matrix_size; i++) {
            for (int j = 0; j < matrix_size; j++)
                if (board[i][j].getStatus() && board[i][j].getTile() == null) {
                    tilesTaken.add(new Coordinates(i, j));
                    n--;
                    if (n == 0) return;
                }
        }
    }

    @TestOnly
    public void clearBoard() {
        for (int i = 0; i < matrix_size; i++) {
            for (int j = 0; j < matrix_size; j++) {
                board[i][j].setTile(null);
            }
        }
    }

    @TestOnly
    public void putTilesOnBoard(List<Tile> tiles, List<Coordinates> coordinates) {
        for (int i = 0; i < tiles.size(); i++) {
            board[coordinates.get(i).x()][coordinates.get(i).y()].setTile(tiles.get(i));
        }
    }

    @TestOnly
    public void enough(Bag bag) throws CantRefillBoardException {
        if (tilesTaken.size() > bag.getLastTiles()) throw new CantRefillBoardException();
    }
}