package Server.Model.LivingRoom.CommonGoal;

import Exception.CommonGoal.NullPlayerException;
import Enumeration.Color;
import Server.Model.LivingRoom.CommonGoal.StaircaseGoal;
import Server.Model.Player.PersonalGoal;
import Server.Model.Player.Player;
import Server.Model.Player.Shelf;
import Utils.Tile;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class StaircaseGoalTest {

    private Player player;
    private PersonalGoal pGoal;
    private StaircaseGoal staircaseGoal;
    private Shelf shelf;
    private JsonObject jsonObject;
    private List<Integer> tokenList;


    @BeforeEach
    void setUp() {
        JsonArray array;
        {
            try {
                array = decoPersonal();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        Random random = new Random();
        pGoal = new PersonalGoal(array.remove(random.nextInt(array.size())).getAsJsonObject());
        player = new Player("ale", pGoal);

        tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);

        jsonObject = new JsonObject();
        jsonObject.addProperty("description", "Test goal");
        jsonObject.addProperty("enum", 1);
    }

    @Test
    void checkStairLeftDown() throws NullPlayerException {
        staircaseGoal = new StaircaseGoal(tokenList, jsonObject);

        shelf = player.getMyShelf();
        shelf.placeTile(new Tile(Color.PINK), 5, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 1);
        shelf.placeTile(new Tile(Color.BLUE), 5, 2);
        shelf.placeTile(new Tile(Color.PINK), 5, 3);
        shelf.placeTile(new Tile(Color.PINK), 5, 4);

        shelf.placeTile(new Tile(Color.GREEN), 4, 0);
        shelf.placeTile(new Tile(Color.GREEN), 4, 1);
        shelf.placeTile(new Tile(Color.CYAN), 4, 2);
        shelf.placeTile(new Tile(Color.BLUE), 4, 3);

        shelf.placeTile(new Tile(Color.BLUE), 3, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 1);
        shelf.placeTile(new Tile(Color.BLUE), 3, 2);

        shelf.placeTile(new Tile(Color.CYAN), 2, 0);
        shelf.placeTile(new Tile(Color.BLUE), 2, 1);

        shelf.placeTile(new Tile(Color.CYAN), 1, 0);

        staircaseGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, staircaseGoal.getAccomplished().size());
    }

    @Test
    void checkNoStairLeftDown() throws NullPlayerException {
        staircaseGoal = new StaircaseGoal(tokenList, jsonObject);

        shelf = player.getMyShelf();
        shelf.placeTile(new Tile(Color.PINK), 5, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 1);
        shelf.placeTile(new Tile(Color.BLUE), 5, 2);
        shelf.placeTile(new Tile(Color.PINK), 5, 3);
        shelf.placeTile(new Tile(Color.PINK), 5, 4);

        shelf.placeTile(new Tile(Color.GREEN), 4, 0);
        shelf.placeTile(new Tile(Color.GREEN), 4, 1);
        shelf.placeTile(new Tile(Color.CYAN), 4, 2);
        shelf.placeTile(new Tile(Color.BLUE), 4, 3);

        shelf.placeTile(new Tile(Color.BLUE), 3, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 1);
        shelf.placeTile(new Tile(Color.BLUE), 3, 2);

        shelf.placeTile(new Tile(Color.CYAN), 2, 0);
        shelf.placeTile(new Tile(Color.BLUE), 2, 1);

        shelf.placeTile(new Tile(Color.CYAN), 1, 0);

        shelf.placeTile(new Tile(Color.PINK), 3, 3); //tile that broke the check

        staircaseGoal.check(player);

        assertEquals(0, player.getScore());
        assertEquals(0, staircaseGoal.getAccomplished().size());
    }




    private JsonArray decoPersonal() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader("src/main/resources/personalGoal.json"));
        return gson.fromJson(reader, JsonArray.class);
    }
}