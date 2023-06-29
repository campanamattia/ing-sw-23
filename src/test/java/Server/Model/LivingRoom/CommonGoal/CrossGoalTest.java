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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CrossGoalTest {

    private Player player;
    private CrossGoal crossGoal;
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
    void checkNoCross() throws NullPlayerException {

        jsonObject.addProperty("numGroup", 2);
        crossGoal = new CrossGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.BLUE), 1, 1);
        shelf.placeTile(new Tile(Color.YELLOW), 1, 2);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 2);
        shelf.placeTile(new Tile(Color.BLUE), 3, 3);
        shelf.placeTile(new Tile(Color.YELLOW), 4, 1);
        shelf.placeTile(new Tile(Color.WHITE), 4, 3);

        crossGoal.check(player);

        assertTrue(crossGoal.getAccomplished().isEmpty());
        assertTrue(crossGoal.getScoringToken().contains(2));
        assertEquals(0, player.getSharedScore());
    }

    @Test
    public void testCheckTwoGroup() throws NullPlayerException {

        jsonObject.addProperty("numGroup", 2);
        crossGoal = new CrossGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.BLUE), 1, 1);
        shelf.placeTile(new Tile(Color.BLUE), 2, 2);
        shelf.placeTile(new Tile(Color.BLUE), 1, 3);
        shelf.placeTile(new Tile(Color.BLUE), 3, 1);
        shelf.placeTile(new Tile(Color.BLUE), 3, 3);
        shelf.placeTile(new Tile(Color.BLUE), 4, 2);
        shelf.placeTile(new Tile(Color.BLUE), 5, 1);
        shelf.placeTile(new Tile(Color.BLUE), 5, 3);

        crossGoal.check(player);

        assertTrue(crossGoal.getAccomplished().contains("ale"));
        assertTrue(crossGoal.getScoringToken().contains(2));
        assertFalse(crossGoal.getScoringToken().contains(4));
        assertEquals(4, player.getSharedScore());
    }

    @Test
    public void testCheckWrongNumGroup() throws NullPlayerException {

        jsonObject.addProperty("numGroup", 2);
        crossGoal = new CrossGoal(tokenList, jsonObject);

        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 3, 2);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 3, 4);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 4, 3);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 5, 2);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 5, 4);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 3, 3);

        crossGoal.check(player);

        assertTrue(crossGoal.getAccomplished().isEmpty());
        assertTrue(crossGoal.getScoringToken().contains(4));
        assertEquals(0, player.getSharedScore());
    }

    @Test
    public void testCheckWithNullPlayer() {

        jsonObject.addProperty("numGroup", 2);
        CrossGoal crossGoal = new CrossGoal(tokenList, jsonObject);

        assertThrows(NullPlayerException.class, () -> crossGoal.check(null));
    }


    private JsonArray decoPersonal() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader("src/test/resources/personalGoal.json"));
        return gson.fromJson(reader, JsonArray.class);
    }
}