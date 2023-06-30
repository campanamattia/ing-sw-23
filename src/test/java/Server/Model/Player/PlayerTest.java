package Server.Model.Player;

import Utils.Tile;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerTest {
    private Player player;
    private PersonalGoal personalGoal;

    @BeforeEach
    public void setUp() {
        personalGoal = new PersonalGoal(decoPersonalGoal());
        player = new Player("player1", personalGoal);
    }

    @Test
    public void testInsert() {
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(null));
        tiles.add(new Tile(null));
        tiles.add(new Tile(null));

        Assertions.assertDoesNotThrow(() -> player.insert(0, tiles));
    }

    @Test
    public void testUpdateSharedScore() {
        player.updateSharedScore(10);
        Assertions.assertEquals(10, player.getSharedScore());
    }

    @Test
    public void testUpdatePersonalScore() {
        player.updatePersonalScore(20);
        Assertions.assertEquals(20, player.getPersonalScore());
    }

    @Test
    public void testUpdatePatternScore() {
        player.updatePatternScore(30);
        Assertions.assertEquals(30, player.getPatternScore());
    }

    @Test
    public void testSetOnline() {
        player.setOnline(false);
        Assertions.assertFalse(player.isOnline());
    }

    @Test
    public void testEndGame() {
        PersonalGoal mockPersonalGoal = new PersonalGoal(decoPersonalGoal());
        mockPersonalGoal.check(new Tile[6][5]);
        player = new Player("player1", mockPersonalGoal);

        player.endGame();
        Assertions.assertEquals(0, player.getPersonalScore());
    }

    @Test
    public void testGetTotalScore() {
        player.updateSharedScore(10);
        player.updatePersonalScore(20);
        player.updatePatternScore(30);

        int totalScore = player.getTotalScore();
        Assertions.assertEquals(60, totalScore);
    }

    @Test
    public void testGetPlayerID() {
        Assertions.assertEquals("player1", player.getPlayerID());
    }

    @Test
    public void testGetPersonalGoal() {
        Assertions.assertEquals(personalGoal, player.getPersonalGoal());
    }

    @Test
    public void testGetMyShelf() {
        Assertions.assertNotNull(player.getMyShelf());
    }

    @Test
    public void testIsOnline() {
        Assertions.assertTrue(player.isOnline());
    }

    @Test
    public void testEquals() {
        Assertions.assertTrue(player.equals("player1"));
        Assertions.assertFalse(player.equals("player2"));
    }

    private JsonObject decoPersonalGoal() {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("settings/personalGoal.json"))));
        JsonArray array =  gson.fromJson(reader, JsonArray.class);
        return array.get(0).getAsJsonObject();
    }
}
