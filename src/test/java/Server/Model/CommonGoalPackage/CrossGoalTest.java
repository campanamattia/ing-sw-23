package Server.Model.CommonGoalPackage;

import Exception.CommonGoal.NullPlayerException;
import Server.Model.*;
import Enumeration.Color;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CrossGoalTest {

    JsonArray array;
    {
        try {
            array = decoPersonal("src/main/resources/personalgoal.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Random random = new Random();
    PersonalGoal pGoal = new PersonalGoal(array.remove(random.nextInt(array.size())).getAsJsonObject());



    @Test
    void checkNoCross() throws NullPlayerException {
        List<Integer> tokenList = new ArrayList<>();
        tokenList.add(2);
        tokenList.add(4);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 1);
        jsonObject.addProperty("description", "Cross goal test");
        jsonObject.addProperty("numGroup", 2);
        CrossGoal crossGoal = new CrossGoal(tokenList, jsonObject);

        Player player = new Player("p1", pGoal);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 1, 1);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 1, 2);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 2, 2);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 3, 3);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 4, 1);
        player.getMyShelf().placeTile(new Tile(Color.WHITE), 4, 3);

        crossGoal.check(player);

        assertTrue(crossGoal.getAccomplished().isEmpty());
        assertTrue(crossGoal.getScoringToken().contains(2));
        assertEquals(0, player.getScore());
    }

    @Test
    public void testCheckTwoGroup() throws NullPlayerException {
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 1);
        jsonObject.addProperty("description", "Cross goal test");
        jsonObject.addProperty("numGroup", 2);
        CrossGoal crossGoal = new CrossGoal(tokenList, jsonObject);

        Player player = new Player("alessio", pGoal);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 1, 1);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 2, 2);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 1, 3);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 3, 1);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 3, 3);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 4, 2);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 5, 1);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 5, 3);

        crossGoal.check(player);

        assertTrue(crossGoal.getAccomplished().contains("alessio"));
        assertTrue(crossGoal.getScoringToken().contains(2));
        assertFalse(crossGoal.getScoringToken().contains(4));
        assertEquals(4, player.getScore());
    }

    @Test
    public void testCheckWrongNumGroup() throws NullPlayerException {
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(3);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 1);
        jsonObject.addProperty("description", "Cross goal test");
        jsonObject.addProperty("numGroup", 2);
        CrossGoal crossGoal = new CrossGoal(tokenList, jsonObject);

        Player player = new Player("alessio", pGoal);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 3, 2);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 3, 4);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 4, 3);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 5, 2);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 5, 4);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 3, 3);

        crossGoal.check(player);

        assertTrue(crossGoal.getAccomplished().isEmpty());
        assertTrue(crossGoal.getScoringToken().contains(3));
        assertEquals(0, player.getScore());
    }

    @Test
    public void testCheckWithNullPlayer() {
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(3);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 1);
        jsonObject.addProperty("description", "Cross goal test");
        jsonObject.addProperty("numGroup", 2);
        CrossGoal crossGoal = new CrossGoal(tokenList, jsonObject);

        assertThrows(NullPlayerException.class, () -> {
            crossGoal.check(null);
        });
    }


    private JsonArray decoPersonal(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        return gson.fromJson(reader, JsonArray.class);
    }
}