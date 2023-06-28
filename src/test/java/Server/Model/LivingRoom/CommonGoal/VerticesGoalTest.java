package Server.Model.LivingRoom.CommonGoal;

import Exception.CommonGoal.NullPlayerException;
import Enumeration.Color;
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

class VerticesGoalTest {

    private Player player;
    private VerticesGoal verticesGoal;
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

        tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);

        jsonObject = new JsonObject();
        jsonObject.addProperty("description", "Test goal");
        jsonObject.addProperty("enum", 1);

    }

    @Test
    void checkVertices() throws NullPlayerException {
        verticesGoal = new VerticesGoal(tokenList, jsonObject);

        shelf = player.getMyShelf();

        shelf.placeTile(new Tile(Color.BLUE), 0, 0);
        shelf.placeTile(new Tile(Color.BLUE), 0, 4);
        shelf.placeTile(new Tile(Color.BLUE), 5, 0);
        shelf.placeTile(new Tile(Color.BLUE), 5, 4);

        verticesGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, verticesGoal.getScoringToken().size());
    }

    @Test
    void checkNoVertices() throws NullPlayerException {
        verticesGoal = new VerticesGoal(tokenList, jsonObject);

        shelf = player.getMyShelf();

        shelf.placeTile(new Tile(Color.GREEN), 0, 0);
        shelf.placeTile(new Tile(Color.BLUE), 0, 4);
        shelf.placeTile(new Tile(Color.BLUE), 5, 0);
        shelf.placeTile(new Tile(Color.BLUE), 5, 4);

        verticesGoal.check(player);

        assertEquals(0, player.getScore());
        assertEquals(2, verticesGoal.getScoringToken().size());
    }



    private JsonArray decoPersonal() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader("src/test/resources/personalGoal.json"));
        return gson.fromJson(reader, JsonArray.class);
    }
}