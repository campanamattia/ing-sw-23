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

import static Server.Model.LivingRoom.CommonGoal.GroupAdjacentGoal.countSameAdjacent;
import static org.junit.jupiter.api.Assertions.assertEquals;


class GroupAdjacentGoalTest {

    private Player player;
    private GroupAdjacentGoal groupAdjacentGoal;
    private Shelf shelf;
    private JsonObject jsonObject;
    private List<Integer> tokenList;

    @BeforeEach
    public void setUp() {
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
    void checkThreeGroupOfFour() throws NullPlayerException {

        jsonObject.addProperty("numGroup", 3);
        jsonObject.addProperty("numAdjacent", 4);
        groupAdjacentGoal = new GroupAdjacentGoal(tokenList, jsonObject);


        shelf.placeTile(new Tile(Color.PINK),2,1);
        shelf.placeTile(new Tile(Color.PINK),3,0);
        shelf.placeTile(new Tile(Color.PINK),3,1);
        shelf.placeTile(new Tile(Color.PINK),4,0);
        shelf.placeTile(new Tile(Color.PINK),4,1);
        shelf.placeTile(new Tile(Color.PINK),5,0);
        shelf.placeTile(new Tile(Color.PINK),5,1);

        shelf.placeTile(new Tile(Color.BLUE),3,3);
        shelf.placeTile(new Tile(Color.BLUE),4,2);
        shelf.placeTile(new Tile(Color.BLUE),4,3);
        shelf.placeTile(new Tile(Color.BLUE),5,2);
        shelf.placeTile(new Tile(Color.BLUE),5,3);

        shelf.placeTile(new Tile(Color.WHITE),2,2);
        shelf.placeTile(new Tile(Color.WHITE),2,3);
        shelf.placeTile(new Tile(Color.WHITE),2,4);
        shelf.placeTile(new Tile(Color.WHITE),3,2);
        shelf.placeTile(new Tile(Color.WHITE),3,4);
        shelf.placeTile(new Tile(Color.WHITE),4,4);
        shelf.placeTile(new Tile(Color.WHITE),5,4);

        groupAdjacentGoal.check(player);

        assertEquals(4, player.getSharedScore());
    }

    @Test
    void checkFourGroupOfTwoTiles() throws NullPlayerException {
        jsonObject.addProperty("numGroup", 4);
        jsonObject.addProperty("numAdjacent", 2);
        groupAdjacentGoal = new GroupAdjacentGoal(tokenList, jsonObject);


        shelf.placeTile(new Tile(Color.YELLOW),2,3);
        shelf.placeTile(new Tile(Color.YELLOW),2,4);
        shelf.placeTile(new Tile(Color.YELLOW),5,3);

        shelf.placeTile(new Tile(Color.PINK),4,2);
        shelf.placeTile(new Tile(Color.PINK),5,2);

        shelf.placeTile(new Tile(Color.GREEN),4,0);
        shelf.placeTile(new Tile(Color.GREEN),3,1);
        shelf.placeTile(new Tile(Color.GREEN),3,2);

        shelf.placeTile(new Tile(Color.BLUE),3,3);
        shelf.placeTile(new Tile(Color.BLUE),3,4);
        shelf.placeTile(new Tile(Color.BLUE),4,3);
        shelf.placeTile(new Tile(Color.BLUE),4,4);
        shelf.placeTile(new Tile(Color.BLUE),5,4);

        shelf.placeTile(new Tile(Color.CYAN),5,0);
        shelf.placeTile(new Tile(Color.CYAN),5,1);
        shelf.placeTile(new Tile(Color.CYAN),4,1);

        groupAdjacentGoal.check(player);

        assertEquals(4, player.getSharedScore());
        assertEquals(1,groupAdjacentGoal.getScoringToken().size());
    }

    @Test
    void checkNoGroup() throws NullPlayerException {
        jsonObject.addProperty("numGroup", 4);
        jsonObject.addProperty("numAdjacent", 4);
        groupAdjacentGoal = new GroupAdjacentGoal(tokenList, jsonObject);

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

        shelf.placeTile(new Tile(Color.CYAN), 5, 0);
        shelf.placeTile(new Tile(Color.CYAN), 5, 1);
        shelf.placeTile(new Tile(Color.CYAN), 4, 1);

        groupAdjacentGoal.check(player);

        assertEquals(0,player.getSharedScore());
        assertEquals(2,groupAdjacentGoal.getScoringToken().size());
    }

    @Test
    void checkNoGroupOfTwo() throws NullPlayerException {
        jsonObject.addProperty("numGroup", 6);
        jsonObject.addProperty("numAdjacent", 2);
        groupAdjacentGoal = new GroupAdjacentGoal(tokenList, jsonObject);


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

        shelf.placeTile(new Tile(Color.CYAN), 5, 0);
        shelf.placeTile(new Tile(Color.CYAN), 5, 1);
        shelf.placeTile(new Tile(Color.CYAN), 4, 1);

        groupAdjacentGoal.check(player);

        assertEquals(0,player.getSharedScore());
        assertEquals(2,groupAdjacentGoal.getScoringToken().size());
    }

    @Test
    public void testCountAdjacent () {
        jsonObject.addProperty("numGroup", 6);
        jsonObject.addProperty("numAdjacent", 2);
        groupAdjacentGoal = new GroupAdjacentGoal(tokenList, jsonObject);

        boolean [][] visited = new boolean[shelf.numberRows()][shelf.numberColumns()];

        shelf.placeTile(new Tile(Color.BLUE), 0, 0);
        shelf.placeTile(new Tile(Color.BLUE), 1, 0);
        shelf.placeTile(new Tile(Color.BLUE), 2, 0);
        shelf.placeTile(new Tile(Color.BLUE), 2, 1);
        shelf.placeTile(new Tile(Color.BLUE), 1, 1);

        int count = countSameAdjacent(shelf, visited, 0,0,Color.BLUE);

        assertEquals(5,count);
    }

    @Test
    public void testCountAdjacent2 () {
        jsonObject.addProperty("numGroup", 6);
        jsonObject.addProperty("numAdjacent", 2);
        groupAdjacentGoal = new GroupAdjacentGoal(tokenList, jsonObject);

        boolean [][] visited = new boolean[shelf.numberRows()][shelf.numberColumns()];

        shelf.placeTile(new Tile(Color.BLUE), 0, 0);
        shelf.placeTile(new Tile(Color.BLUE), 1, 0);
        shelf.placeTile(new Tile(Color.BLUE), 2, 0);
        shelf.placeTile(new Tile(Color.BLUE), 2, 1);
        shelf.placeTile(new Tile(Color.BLUE), 1, 1);

        int count = countSameAdjacent(shelf, visited, 1,1,Color.BLUE);

        assertEquals(5,count);
    }

    @Test
    public void testCountAdjacent3 () {
        jsonObject.addProperty("numGroup", 6);
        jsonObject.addProperty("numAdjacent", 2);
        groupAdjacentGoal = new GroupAdjacentGoal(tokenList, jsonObject);

        boolean [][] visited = new boolean[shelf.numberRows()][shelf.numberColumns()];

        shelf.placeTile(new Tile(Color.BLUE), 0, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 1, 0);
        shelf.placeTile(new Tile(Color.YELLOW), 2, 0);
        shelf.placeTile(new Tile(Color.BLUE), 2, 1);
        shelf.placeTile(new Tile(Color.BLUE), 1, 1);

        int count = countSameAdjacent(shelf, visited, 1,1,Color.BLUE);

        assertEquals(2,count);
    }

    private JsonArray decoPersonal() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new FileReader("src/test/resources/personalGoal.json"));
        return gson.fromJson(reader, JsonArray.class);
    }
}