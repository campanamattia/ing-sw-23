package Server.Model.CommonGoalPackage;

import Exception.CommonGoal.NullPlayerException;
import Server.Model.*;
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

class SameNGoalTest {

    private Player player;
    private SameNGoal sameNGoal;
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
        PersonalGoal pGoal = new PersonalGoal(array.remove(random.nextInt(array.size())).getAsJsonObject());
        player = new Player("ale", pGoal);
        shelf = player.getMyShelf();

        tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);

        jsonObject = new JsonObject();
        jsonObject.addProperty("description", "Test goal");
        jsonObject.addProperty("enum", 1);
    }

    @Test
    void check8Tiles() throws NullPlayerException {
        jsonObject.addProperty("numEquals", 8);
        sameNGoal = new SameNGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 2, 3);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 3);

        shelf.placeTile(new Tile(Color.PINK), 4, 2);
        shelf.placeTile(new Tile(Color.PINK), 5, 2);

        shelf.placeTile(new Tile(Color.GREEN), 4, 0);
        shelf.placeTile(new Tile(Color.GREEN), 3, 1);
        shelf.placeTile(new Tile(Color.GREEN), 3, 2);

        shelf.placeTile(new Tile(Color.BLUE), 3, 3);
        shelf.placeTile(new Tile(Color.BLUE), 3, 4);
        shelf.placeTile(new Tile(Color.BLUE), 4, 3);
        shelf.placeTile(new Tile(Color.BLUE), 4, 4);
        shelf.placeTile(new Tile(Color.BLUE), 5, 4);

        shelf.placeTile(new Tile(Color.BLUE), 5, 0);
        shelf.placeTile(new Tile(Color.BLUE), 5, 1);
        shelf.placeTile(new Tile(Color.BLUE), 4, 1);

        sameNGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1,sameNGoal.getAccomplished().size());
    }

    @Test
    void check11Tiles() throws NullPlayerException {
        jsonObject.addProperty("numEquals", 11);
        sameNGoal = new SameNGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 2, 3);
        shelf.placeTile(new Tile(Color.BLUE), 2, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 3);

        shelf.placeTile(new Tile(Color.BLUE), 4, 2);
        shelf.placeTile(new Tile(Color.PINK), 5, 2);

        shelf.placeTile(new Tile(Color.BLUE), 4, 0);
        shelf.placeTile(new Tile(Color.GREEN), 3, 1);
        shelf.placeTile(new Tile(Color.GREEN), 3, 2);

        shelf.placeTile(new Tile(Color.BLUE), 3, 3);
        shelf.placeTile(new Tile(Color.BLUE), 3, 4);
        shelf.placeTile(new Tile(Color.BLUE), 4, 3);
        shelf.placeTile(new Tile(Color.BLUE), 4, 4);
        shelf.placeTile(new Tile(Color.BLUE), 5, 4);

        shelf.placeTile(new Tile(Color.BLUE), 5, 0);
        shelf.placeTile(new Tile(Color.BLUE), 5, 1);
        shelf.placeTile(new Tile(Color.BLUE), 4, 1);

        sameNGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1,sameNGoal.getAccomplished().size());
    }

    @Test
    void checkNo10Tiles() throws NullPlayerException {
        jsonObject.addProperty("numEquals", 11);
        sameNGoal = new SameNGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 2, 3);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 3);

        shelf.placeTile(new Tile(Color.BLUE), 4, 2);
        shelf.placeTile(new Tile(Color.PINK), 5, 2);

        shelf.placeTile(new Tile(Color.YELLOW), 4, 0);
        shelf.placeTile(new Tile(Color.GREEN), 3, 1);
        shelf.placeTile(new Tile(Color.GREEN), 3, 2);

        shelf.placeTile(new Tile(Color.BLUE), 3, 3);
        shelf.placeTile(new Tile(Color.BLUE), 3, 4);
        shelf.placeTile(new Tile(Color.BLUE), 4, 3);
        shelf.placeTile(new Tile(Color.BLUE), 4, 4);
        shelf.placeTile(new Tile(Color.BLUE), 5, 4);

        shelf.placeTile(new Tile(Color.BLUE), 5, 0);
        shelf.placeTile(new Tile(Color.BLUE), 5, 1);
        shelf.placeTile(new Tile(Color.BLUE), 4, 1);

        sameNGoal.check(player);

        assertEquals(0, player.getScore());
        assertEquals(0,sameNGoal.getAccomplished().size());
    }

    private JsonArray decoPersonal() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader("src/main/resources/personalgoal.json"));
        return gson.fromJson(reader, JsonArray.class);
    }
}