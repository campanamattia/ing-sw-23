package Server.Model.LivingRoom;

import Enumeration.Color;
import Exception.Board.CantRefillBoardException;
import Exception.Board.NullTileException;
import Utils.Coordinates;
import Utils.Tile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Bag bag;
    Board board;
    JsonObject board_json;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        // setupBoard
        this.bag = new Bag();
        this.board_json = decoBoard();
        this.board = new Board(board_json,this.bag);
    }

    @Test
    public void convalidateMoveTest() {
        // cell not usable
        assertThrows(NullTileException.class, () -> this.board.convalidateMove(List.of(
                new Coordinates(0, 0)
        )));
        // usable cell
        assertDoesNotThrow(() -> this.board.convalidateMove(List.of(
                new Coordinates(5, 4)
        )));
        // usable cells
        assertDoesNotThrow(() -> this.board.convalidateMove(Arrays.asList(
                new Coordinates(3, 0),
                new Coordinates(4, 0)
        )));
        // cells not adjacent
        assertThrows(NullTileException.class, () -> this.board.convalidateMove(Arrays.asList(
                new Coordinates(0, 0),
                new Coordinates(2, 0)
        )));
    }
    @Test
    public void getTileTest(){
        // getting a tile with success
        assertDoesNotThrow(() -> {
            board.getTiles(List.of(
                    new Coordinates(0, 2)
            ));
        });

        // getting the same tile again
        assertThrows(NullTileException.class, () -> board.getTiles(List.of(
                new Coordinates(0, 2)
        )));

        // getting a tile from a cell that doesn't contain one
        assertThrows(NullTileException.class, () -> board.getTiles(List.of(
                new Coordinates(0, 1)
        )));
    }
    @Test
    public void checkRefillTest() {
        // don't need a refill
        assertDoesNotThrow(() -> board.checkRefill(bag));

        // need a refill but haven't enough tiles in the bag
        assertThrows(CantRefillBoardException.class, () -> {
            this.bag = new Bag();
            this.board_json = decoBoard();
            this.board = new Board(board_json,this.bag);
            this.board.clearBoard();
            this.board.setTilesTaken(110);
            if (this.bag.getLastTiles() < this.board.tilesTakenNumber) {
                throw new CantRefillBoardException();
            }
        });

        // need a refill with success
        assertDoesNotThrow( () -> {
            this.bag = new Bag();
            this.board_json = decoBoard();
            this.board = new Board(board_json,this.bag);
            this.board.clearBoard();
            this.board.setTilesTaken(29);
            board.checkRefill(bag);
        });

        // need a refill with success
        assertDoesNotThrow( () -> {
            this.bag = new Bag();
            this.board_json = decoBoard();
            this.board = new Board(board_json,this.bag);
            this.board.clearBoard();
            this.board.putTilesOnBoard(Arrays.asList(new Tile(Color.CYAN), new Tile(Color.GREEN)), Arrays.asList(new Coordinates(6, 4), new Coordinates(5, 3)));
            this.board.setTilesTaken(27);
            board.checkRefill(bag);
            if (this.board.tilesTakenNumber != 0) {
                throw new CantRefillBoardException();
            }
        });

        // refill with success
        assertDoesNotThrow( () -> {
            this.bag = new Bag();
            this.board_json = decoBoard();
            this.board = new Board(board_json,this.bag);
            this.board.setTilesTaken(20);
            this.board.checkRefill(bag);
        });
    }

    @Test
    public void getBoard(){
        assertTrue(this.board.equals(this.board.getBoard()));
    }

    private JsonObject decoBoard() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader("src/test/resources/board.json"));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.getAsJsonObject(Integer.toString(2));
    }
}