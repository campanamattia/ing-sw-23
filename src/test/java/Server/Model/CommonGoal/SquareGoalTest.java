package Server.Model.CommonGoal;

import Exception.CommonGoal.NullPlayerException;
import Enumeration.Color;
import Server.Model.LivingRoom.CommonGoal.SquareGoal;
import Server.Model.Player.PersonalGoal;
import Server.Model.Player.Player;
import Utils.Tile;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class SquareGoalTest {

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
    void checkOneSquareOnTheSide() throws NullPlayerException {
        // Set up the test
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("dimSquare", 3);
        jsonObject.addProperty("numGroup", 1);

        SquareGoal squareGoal= new SquareGoal(tokenList, jsonObject);

        Player player = new Player("player1",pGoal);

        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 4);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 4);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 5, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 5, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 5, 4);

        squareGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, squareGoal.getAccomplished().size());

    }

    @Test
    void checkOneSquareInCenter() throws NullPlayerException {
        // Set up the test
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("dimSquare", 3);
        jsonObject.addProperty("numGroup", 1);

        SquareGoal squareGoal= new SquareGoal(tokenList, jsonObject);

        Player player = new Player("player1",pGoal);

        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 1);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 1);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 1);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 3);

        squareGoal.check(player);

        assertEquals(4, player.getScore());
        assertEquals(1, squareGoal.getAccomplished().size());
    }

    @Test
    void checkNoSquareInCenter() throws NullPlayerException {
        // Set up the test
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("dimSquare", 3);
        jsonObject.addProperty("numGroup", 1);

        SquareGoal squareGoal= new SquareGoal(tokenList, jsonObject);

        Player player = new Player("player1",pGoal);

        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 1);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 1);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 1);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 4, 4);

        squareGoal.check(player);

        assertEquals(0, player.getScore());
        assertEquals(0, squareGoal.getAccomplished().size());
    }


    @Test
    void checkNoSquareAdjacent() throws NullPlayerException {
        // Set up the test
        Stack<Integer> tokenList = new Stack<>();
        tokenList.add(2);
        tokenList.add(4);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enum", 2);
        jsonObject.addProperty("description", "Test goal description");
        jsonObject.addProperty("dimSquare", 2);
        jsonObject.addProperty("numGroup", 2);

        SquareGoal squareGoal= new SquareGoal(tokenList, jsonObject);

        Player player = new Player("player1",pGoal);

        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 0);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 1);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 2, 3);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 0);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 1);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 2);
        player.getMyShelf().placeTile(new Tile(Color.CYAN), 3, 3);
        player.getMyShelf().placeTile(new Tile(Color.PINK), 0, 0);
        player.getMyShelf().placeTile(new Tile(Color.PINK), 1, 1);

        squareGoal.check(player);

        assertEquals(0, player.getScore());
        assertEquals(0, squareGoal.getAccomplished().size());
    }



    private JsonArray decoPersonal(String filepath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader(filepath));
        return gson.fromJson(reader, JsonArray.class);
    }
}