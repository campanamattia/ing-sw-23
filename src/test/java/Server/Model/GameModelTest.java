package Server.Model;

import Enumeration.Color;
import Utils.ChatMessage;
import Utils.Coordinates;
import Utils.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Exception.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    static GameModel gameModel;
    @BeforeAll
    public static void setUp() {
        try{
            gameModel = new GameModel("test",
                    Arrays.asList("Alice", "Bob", "Carlos")
            );
        }catch (IOException e) {
            fail();
        }
    }

    @Test
    public void updateStatusAndWriteMessage(){
        try{
            ChatMessage text = new ChatMessage(
                    "Alice",
                    "Hello world!",
                    "Bob"
            );
            gameModel.writeChat(text.from(), text.message(), text.to());
        }catch(ChatException e){
            fail();
        }
    }

    @Test
    public void selectedTilesWithNoValidCoordinate(){
        try{
            gameModel.selectedTiles(Arrays.asList(new Coordinates(2,10), new Coordinates(5,4)));
        }catch (BoardException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void selectedTilesWithOutCoordinates(){
        try{
            gameModel.selectedTiles(List.of());
        }catch (BoardException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void NewMessageWithEmptyBody(){
        try{
            ChatMessage text = new ChatMessage(
                    "Alice",
                    "",
                    "Bob"
            );
            gameModel.writeChat(text.from(), text.message(), text.to());
            fail();
        }catch(ChatException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void WrongInputInInsertTiles(){
        try{
            gameModel.insertTiles(Arrays.asList(2, 3), Arrays.asList(new Tile(Color.BLUE), new Tile(Color.GREEN), new Tile(Color.CYAN)), 7);
            fail();
        }catch (PlayerException e){
            System.out.println(e.getMessage());
            assertTrue(true);
        }
    }
}