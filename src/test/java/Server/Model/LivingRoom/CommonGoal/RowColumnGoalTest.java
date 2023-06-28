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

class RowColumnGoalTest {

    private Player player;
    private RowColumnGoal rowColumnGoal;
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
    void checkTwoColumnOfDifferentColor() throws NullPlayerException {
        jsonObject.addProperty("numColumn", "2");
        jsonObject.addProperty("numRow", "-1");
        jsonObject.addProperty("maxDifferent", "-1");

        rowColumnGoal = new RowColumnGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 0, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 1, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 4, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 0);

        shelf.placeTile(new Tile(Color.BLUE), 0, 4);
        shelf.placeTile(new Tile(Color.BLUE), 1, 4);
        shelf.placeTile(new Tile(Color.BLUE), 2, 4);
        shelf.placeTile(new Tile(Color.BLUE), 3, 4);
        shelf.placeTile(new Tile(Color.BLUE), 4, 4);
        shelf.placeTile(new Tile(Color.BLUE), 5, 4);

        rowColumnGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, rowColumnGoal.getAccomplished().size());
    }

    @Test
    void checkTwoColumnOfSameColor() throws NullPlayerException {
        jsonObject.addProperty("numColumn", "2");
        jsonObject.addProperty("numRow", "-1");
        jsonObject.addProperty("maxDifferent", "-1");

        rowColumnGoal = new RowColumnGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 0, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 1, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 4, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 0);

        shelf.placeTile(new Tile(Color.YELLOW), 0, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 1, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 4, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 4);

        shelf.placeTile(new Tile(Color.PINK), 2, 3);
        shelf.placeTile(new Tile(Color.PINK), 5, 1);

        rowColumnGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, rowColumnGoal.getAccomplished().size());
        assertEquals(1, rowColumnGoal.getScoringToken().size());
        assertTrue(rowColumnGoal.getScoringToken().contains(2));
    }

    @Test
    void checkTwoRowOfSameColor() throws NullPlayerException {
        jsonObject.addProperty("numColumn", "-1");
        jsonObject.addProperty("numRow", "2");
        jsonObject.addProperty("maxDifferent", "-1");

        rowColumnGoal = new RowColumnGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 2, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 1);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 2);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 3);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 4);

        shelf.placeTile(new Tile(Color.YELLOW), 3, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 1);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 2);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 3);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 4);

        shelf.placeTile(new Tile(Color.PINK), 1, 3);
        shelf.placeTile(new Tile(Color.PINK), 5, 1);

        rowColumnGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, rowColumnGoal.getAccomplished().size());
        assertEquals(1, rowColumnGoal.getScoringToken().size());
        assertTrue(rowColumnGoal.getScoringToken().contains(2));
    }

    @Test
    void checkOneRowOneColumnAllEquals() throws NullPlayerException {
        jsonObject.addProperty("numColumn", "1");
        jsonObject.addProperty("numRow", "1");
        jsonObject.addProperty("maxDifferent", "-1");

        rowColumnGoal = new RowColumnGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 0, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 1, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 4, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 4);

        shelf.placeTile(new Tile(Color.YELLOW), 3, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 1);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 2);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 3);

        shelf.placeTile(new Tile(Color.PINK), 1, 3);
        shelf.placeTile(new Tile(Color.PINK), 5, 1);

        rowColumnGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, rowColumnGoal.getAccomplished().size());
        assertEquals(1, rowColumnGoal.getScoringToken().size());
        assertTrue(rowColumnGoal.getScoringToken().contains(2));
    }

    @Test
    void checkThreeColumnWithMaxDifferent3() throws NullPlayerException {
        jsonObject.addProperty("numColumn", "2");
        jsonObject.addProperty("numRow", "-1");
        jsonObject.addProperty("maxDifferent", "3");

        rowColumnGoal = new RowColumnGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 0, 4);
        shelf.placeTile(new Tile(Color.PINK), 1, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 3, 4);
        shelf.placeTile(new Tile(Color.BLUE), 4, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 4);

        shelf.placeTile(new Tile(Color.CYAN), 0, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 1, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 0);
        shelf.placeTile(new Tile(Color.GREEN), 3, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 4, 0);
        shelf.placeTile(new Tile(Color.GREEN), 5, 0);

        shelf.placeTile(new Tile(Color.PINK), 1, 3);
        shelf.placeTile(new Tile(Color.PINK), 5, 1);

        rowColumnGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, rowColumnGoal.getAccomplished().size());
        assertEquals(1, rowColumnGoal.getScoringToken().size());
        assertTrue(rowColumnGoal.getScoringToken().contains(2));
    }

    @Test
    void checkNoThreeColumnWithMaxDifferent3() throws NullPlayerException {
        jsonObject.addProperty("numColumn", "2");
        jsonObject.addProperty("numRow", "-1");
        jsonObject.addProperty("maxDifferent", "3");

        rowColumnGoal = new RowColumnGoal(tokenList, jsonObject);

        shelf.placeTile(new Tile(Color.YELLOW), 0, 4);
        shelf.placeTile(new Tile(Color.PINK), 1, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 4);
        shelf.placeTile(new Tile(Color.CYAN), 3, 4);
        shelf.placeTile(new Tile(Color.BLUE), 4, 4);
        shelf.placeTile(new Tile(Color.YELLOW), 5, 4);

        shelf.placeTile(new Tile(Color.CYAN), 0, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 1, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 0);
        shelf.placeTile(new Tile(Color.GREEN), 3, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 4, 0);
        shelf.placeTile(new Tile(Color.GREEN), 5, 0);

        shelf.placeTile(new Tile(Color.PINK), 1, 3);
        shelf.placeTile(new Tile(Color.PINK), 5, 1);

        rowColumnGoal.check(player);

        assertEquals(0, player.getScore());
        assertEquals(0, rowColumnGoal.getAccomplished().size());
        assertEquals(2, rowColumnGoal.getScoringToken().size());
        assertTrue(rowColumnGoal.getScoringToken().contains(2));
    }







    private JsonArray decoPersonal() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader("src/test/resources/personalGoal.json"));
        return gson.fromJson(reader, JsonArray.class);
    }
}