package Server.Model;

import Server.Exception.Board.CantRefillBoardException;
import Server.Exception.Board.NoValidMoveException;
import Server.Exception.Board.NullTileException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
        this.board_json = decoBoard("src/main/resources/board.json");
        this.board = new Board(board_json,this.bag);
    }

    @Test
    public void convalidateMoveTest() {
        // cell not usable
        NoValidMoveException noValidMoveException = assertThrows(NoValidMoveException.class, () -> {
            this.board.convalidateMove(Arrays.asList(
                    new Coordinates(0, 0)
            ));
        });
        // usable cell
        assertDoesNotThrow(() -> {
            this.board.convalidateMove(Arrays.asList(
                    new Coordinates(5, 4)
            ));
        });
        // usable cells
        assertDoesNotThrow(() -> {
            this.board.convalidateMove(Arrays.asList(
                    new Coordinates(3, 0),
                    new Coordinates(4, 0)
            ));
        });
        // cells not adjacent
        assertThrows(NoValidMoveException.class, () -> {
            this.board.convalidateMove(Arrays.asList(
                    new Coordinates(0, 0),
                    new Coordinates(2, 0)
            ));
        });
    }
    @Test
    public void getTilesTest(){
        // getting a tile with success
        assertDoesNotThrow(() -> {
            board.getTiles(Arrays.asList(
                    new Coordinates(0, 2)
            ));
        });

        // getting the same tile again
        assertThrows(NullTileException.class, () -> {
            board.getTiles(Arrays.asList(
                    new Coordinates(0, 2)
            ));
        });

        // getting a tile from a cell that doesn't contain one
        assertThrows(NullTileException.class, () -> {
            board.getTiles(Arrays.asList(
                    new Coordinates(0, 1)
            ));
        });
    }
    @Test
    public void checkRefillTest() throws CantRefillBoardException {
        assertDoesNotThrow(() -> {
            board.checkRefill(bag);
        });
    }
    @Test

    private JsonObject decoBoard(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.getAsJsonObject(Integer.toString(2));
    }
}