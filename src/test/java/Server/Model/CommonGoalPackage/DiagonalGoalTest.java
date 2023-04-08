package Server.Model.CommonGoalPackage;

import Server.Exception.CommonGoal.NullPlayerException;
import Server.Model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

class DiagonalGoalTest {

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
    void testCheckNullShelf() throws NullPlayerException {
        // Set up the test
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("numDiagonal", 2);

        DiagonalGoal diagonalGoal = new DiagonalGoal(tokenList, jsonObject);

        Player player = new Player("player1",pGoal);

        // Perform the test
        diagonalGoal.check(player);

        assertTrue(diagonalGoal.getAccomplished().isEmpty());
        assertEquals(2, tokenList.size());
        assertEquals(0, player.getScore());
    }

    @Test
    void testCheckNoDiagonal() throws NullPlayerException {
        // Set up the test
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("numDiagonal", 1);

        DiagonalGoal diagonalGoal = new DiagonalGoal(tokenList, jsonObject);

        Player player = new Player("player1",pGoal);

        player.getMyShelf().placeTile(new Tile(Color.BLUE), 1, 1);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 1, 2);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 2, 2);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 3, 3);
        player.getMyShelf().placeTile(new Tile(Color.YELLOW), 4, 1);
        player.getMyShelf().placeTile(new Tile(Color.WHITE), 4, 3);

        // Perform the test
        diagonalGoal.check(player);

        assertTrue(diagonalGoal.getAccomplished().isEmpty());
        assertEquals(2, tokenList.size());
        assertEquals(0, player.getScore());
    }

    @Test
    void testCheckOneDiagonal() throws NullPlayerException {
        // Set up the test
        List<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("numDiagonal", 1);

        DiagonalGoal diagonalGoal = new DiagonalGoal(tokenList, jsonObject);

        Player player = new Player("player1",pGoal);

        player.getMyShelf().placeTile(new Tile(Color.GREEN), 0,0);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 1, 1);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 2, 2);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 3, 3);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 4, 4);
        player.getMyShelf().placeTile(new Tile(Color.WHITE), 4, 3);

        // Perform the test
        diagonalGoal.check(player);

        assertTrue(diagonalGoal.getAccomplished().contains(player.getID()));
        assertEquals(1,diagonalGoal.getScoringToken().size());
        assertEquals(4, player.getScore());
        assertEquals(2 ,diagonalGoal.getScoringToken().peek());
    }

    @Test
    void testCheckTwoDiagonal() throws NullPlayerException {
        // Set up the test
        List<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("numDiagonal", 2);

        DiagonalGoal diagonalGoal = new DiagonalGoal(tokenList, jsonObject);

        Player player = new Player("player1",pGoal);

        player.getMyShelf().placeTile(new Tile(Color.GREEN), 0,4);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 1, 4);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 1, 3);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 2, 3);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 2, 2);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 3, 2);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 3, 1);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 4, 1);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 4, 2);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 5, 0);
        player.getMyShelf().placeTile(new Tile(Color.GREEN), 4, 0);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 4, 3);
        player.getMyShelf().placeTile(new Tile(Color.BLUE), 5, 4);

        // Perform the test
        diagonalGoal.check(player);

        assertTrue(diagonalGoal.getAccomplished().contains(player.getID()));
        assertEquals(1,diagonalGoal.getScoringToken().size());
        assertEquals(4, player.getScore());
        assertEquals(2 ,diagonalGoal.getScoringToken().peek());
    }

    @Test
    void testCheckDifferentPlayer() throws NullPlayerException {
        // Set up the test
        List<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("numDiagonal", 1);

        DiagonalGoal diagonalGoal = new DiagonalGoal(tokenList, jsonObject);

        Player player1 = new Player("alessio",pGoal);
        Player player2 = new Player("fostidic", pGoal);


        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 0,4);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 1, 4);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 1, 3);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 2, 3);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 2, 2);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 3, 2);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 3, 1);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 4, 1);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 4, 2);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 5, 0);
        player1.getMyShelf().placeTile(new Tile(Color.GREEN), 4, 0);
        player1.getMyShelf().placeTile(new Tile(Color.BLUE), 4, 3);
        player1.getMyShelf().placeTile(new Tile(Color.BLUE), 5, 4);

        player2.getMyShelf().placeTile(new Tile(Color.WHITE), 0,0);
        player2.getMyShelf().placeTile(new Tile(Color.WHITE), 1,1);
        player2.getMyShelf().placeTile(new Tile(Color.WHITE), 2,2);
        player2.getMyShelf().placeTile(new Tile(Color.WHITE), 3,3);
        player2.getMyShelf().placeTile(new Tile(Color.WHITE), 4,4);


        // Perform the test
        diagonalGoal.check(player1);
        diagonalGoal.check(player2);

        assertEquals(2,diagonalGoal.getAccomplished().size());
        assertEquals(0, diagonalGoal.getScoringToken().size());
        assertEquals(4, player1.getScore());
        assertEquals(2,player2.getScore());
    }





    private JsonArray decoPersonal(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        return gson.fromJson(reader, JsonArray.class);
    }
}