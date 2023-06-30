package Server.Model;

import Enumeration.Color;
import Exception.Board.NoValidMoveException;
import Exception.Board.NullTileException;
import Exception.Player.InvalidInputException;
import Exception.Player.PlayerNotFoundException;
import Server.Model.LivingRoom.Board;
import Server.Model.Player.PersonalGoal;
import Server.Model.Player.Player;
import Utils.ChatMessage;
import Utils.ChatRoom;
import Utils.Coordinates;
import Utils.Tile;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Exception.*;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    static GameModel gameModel;

    @BeforeAll
    public static void setUp() {
        gameModel = new GameModel("test", Arrays.asList("Alice", "Bob", "Carlos"));
    }

    @Test
    public void updateStatusAndWriteMessage() {
        try {
            ChatMessage text = new ChatMessage(
                    "Alice",
                    "Hello world!",
                    "Bob"
            );
            gameModel.writeChat(text.from(), text.message(), text.to());
        } catch (ChatException e) {
            fail();
        }
    }

    @Test
    public void writeChat() {
        try {
            ChatMessage text = new ChatMessage(
                    "Alice",
                    "Hello world!",
                    null
            );
            gameModel.writeChat(text.from(), text.message(), text.to());
        } catch (ChatException e) {
            fail();
        }
    }

    @Test
    public void selectedTilesWithNoValidCoordinate() {
        try {
            gameModel.selectTiles(Arrays.asList(new Coordinates(2, 10), new Coordinates(5, 4)));
        } catch (BoardException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void selectedTilesWithOutCoordinates() {
        try {
            gameModel.selectTiles(List.of());
        } catch (BoardException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void NewMessageWithEmptyBody() {
        try {
            ChatMessage text = new ChatMessage(
                    "Alice",
                    "",
                    "Bob"
            );
            gameModel.writeChat(text.from(), text.message(), text.to());
            fail();
        } catch (ChatException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void checkRefill(){
        try {
            gameModel.checkRefill();
        } catch (BoardException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

    @Test
    public void getCommonGoals(){
        assertEquals(2, gameModel.getCommonGoals().size());
    }

    @Test
    public void getBoard(){
        Board board = gameModel.getBoard();
        assertNotNull(board);
    }

    @Test
    public void getChatRoom(){
        ChatRoom room = gameModel.getChatRoom();
        assertNotNull(room);
    }

    @Test
    public void getTalent(){
        assertNotNull( gameModel.getTalent());
    }

    @Test
    public void WrongInputInInsertTiles() {
        try {
            gameModel.insertTiles(Arrays.asList(2, 3), Arrays.asList(new Tile(Color.BLUE), new Tile(Color.GREEN), new Tile(Color.CYAN)), 7);
            fail();
        } catch (PlayerException e) {
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void selectTilesWithInvalidMoveTest() {
        List<Coordinates> coordinates = Arrays.asList(new Coordinates(0, 0), new Coordinates(1, 0));
        assertThrows(NullTileException.class, () -> gameModel.selectTiles(coordinates));
    }

    @Test
    public void selectTilesWithNullTileTest() {
        List<Coordinates> coordinates = Arrays.asList(new Coordinates(2, 0), new Coordinates(3, 0));
        assertThrows(NullTileException.class, () -> gameModel.selectTiles(coordinates));
    }

    @Test
    public void insertTilesWithInvalidInputTest() {
        List<Tile> tiles = Arrays.asList(new Tile(Color.BLUE), new Tile(Color.GREEN), new Tile(Color.CYAN));
        assertThrows(InvalidInputException.class, () -> gameModel.insertTiles(Arrays.asList(2, 3), tiles, 7));
    }

    @Test
    public void writeChatWithEmptyMessageTest() {
        assertThrows(ChatException.class, () -> gameModel.writeChat("Alice", "", "Bob"));
    }

    @Test
    public void getPlayerWithValidIDTest() {
        assertDoesNotThrow(() -> {
            Player player = gameModel.getPlayer("Alice");
            assertNotNull(player);
            assertEquals("Alice", player.getPlayerID());
        });
    }

    @Test
    public void getPlayerWithInvalidIDTest() {
        assertThrows(PlayerNotFoundException.class, () -> gameModel.getPlayer("John"));
    }


    @Test
    public void getPlayers3() {
        assertEquals(3, gameModel.getPlayers().size());
    }

    @Test
    public void getLobbyID() {
        assertEquals("test", gameModel.getLobbyID());
    }

    @Test
    public void getFirstPlayerTest() {
        assertEquals("Alice", gameModel.getFirstPlayer());
    }

    @Test
    public void completeTurn(){
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Color.BLUE));
        tiles.add(new Tile(Color.GREEN));
        assertDoesNotThrow(() -> gameModel.completeTurn(tiles));
    }

    @Test
    public void cleanInsert(){
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Color.BLUE));
        tiles.add(new Tile(Color.GREEN));
        assertDoesNotThrow(() -> gameModel.insertTiles(integers, tiles, 0));
    }

    @Test
    public void cleanSelect(){
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(new Coordinates(0,3));
        assertDoesNotThrow(() -> gameModel.selectTiles(coordinates));
    }

    @Test
    public void setCurrentPlayerTest() {
        Player player = new Player("John", decoPersonalGoal());
        gameModel.setCurrentPlayer(player);
        assertEquals(player, gameModel.getCurrentPlayer());
    }

    @Test
    public void tooManyTiles(){
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(new Coordinates(0,1));
        coordinates.add(new Coordinates(0,2));
        coordinates.add(new Coordinates(0,3));
        coordinates.add(new Coordinates(0,4));
        coordinates.add(new Coordinates(0,5));
        coordinates.add(new Coordinates(0,6));
        coordinates.add(new Coordinates(0,7));
        coordinates.add(new Coordinates(0,8));
        coordinates.add(new Coordinates(0,9));
        assertThrows(NoValidMoveException.class, () -> gameModel.selectTiles(coordinates));
    }

    private PersonalGoal decoPersonalGoal() {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("settings/personalGoal.json"))));
        JsonArray array = gson.fromJson(reader, JsonArray.class);
        return new PersonalGoal(array.get(0).getAsJsonObject());
    }
}