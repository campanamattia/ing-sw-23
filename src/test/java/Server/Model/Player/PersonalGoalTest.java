package Server.Model.Player;
import Enumeration.Color;
import Utils.Tile;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.Objects;

public class PersonalGoalTest {
    private PersonalGoal personalGoal;
    private Tile[][] myShelf;

    @BeforeEach
    public void setUp() {
        JsonArray jsonArray = decoPersonal();
        personalGoal = new PersonalGoal(jsonArray.get(0).getAsJsonObject());
        myShelf = new Tile[6][5];
    }

    @Test
    public void test6() {
        myShelf[0][0] = new Tile(Color.PINK);
        myShelf[5][2] = new Tile(Color.CYAN);
        myShelf[0][2] = new Tile(Color.BLUE);
        myShelf[1][4] = new Tile(Color.GREEN);
        myShelf[3][1] = new Tile(Color.YELLOW);
        myShelf[2][3] = new Tile(Color.WHITE);
        int score = personalGoal.check(myShelf);
        Assertions.assertEquals(12, score);
    }

    @Test
    public void test5() {
        myShelf[5][2] = new Tile(Color.CYAN);
        myShelf[0][2] = new Tile(Color.BLUE);
        myShelf[1][4] = new Tile(Color.GREEN);
        myShelf[3][1] = new Tile(Color.YELLOW);
        myShelf[2][3] = new Tile(Color.WHITE);
        int score = personalGoal.check(myShelf);
        Assertions.assertEquals(9, score);
    }

    @Test
    public void test4() {
        myShelf[0][2] = new Tile(Color.BLUE);
        myShelf[1][4] = new Tile(Color.GREEN);
        myShelf[3][1] = new Tile(Color.YELLOW);
        myShelf[2][3] = new Tile(Color.WHITE);
        int score = personalGoal.check(myShelf);
        Assertions.assertEquals(6, score);
    }

    @Test
    public void test3() {
        myShelf[1][4] = new Tile(Color.GREEN);
        myShelf[3][1] = new Tile(Color.YELLOW);
        myShelf[2][3] = new Tile(Color.WHITE);
        int score = personalGoal.check(myShelf);
        Assertions.assertEquals(4, score);
    }

    @Test
    public void test2() {
        myShelf[3][1] = new Tile(Color.YELLOW);
        myShelf[2][3] = new Tile(Color.WHITE);
        int score = personalGoal.check(myShelf);
        Assertions.assertEquals(2, score);
    }

    @Test
    public void test1() {
        myShelf[2][3] = new Tile(Color.WHITE);
        int score = personalGoal.check(myShelf);
        Assertions.assertEquals(1, score);
    }

    @Test
    public void test0() {
        int score = personalGoal.check(myShelf);
        Assertions.assertEquals(0, score);
    }


    @Test
    public void testGetPersonalGoal() {
        Tile[][] pg = personalGoal.getPersonalGoal();
        Assertions.assertNotNull(pg);
        Assertions.assertEquals(6, pg.length);
        Assertions.assertEquals(5, pg[0].length);
        Assertions.assertNotNull(pg[0][0]);
        Assertions.assertNull(pg[1][1]);
    }

    private JsonArray decoPersonal() {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("settings/personalGoal.json"))));
        return gson.fromJson(reader, JsonArray.class);
    }
}
