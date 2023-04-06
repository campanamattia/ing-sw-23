package Server.Model;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    GameModel gameModel;
    @BeforeEach
    public void setUp() {
        try{
            gameModel = new GameModel(
                    UUID.fromString("123e4567-e89b-12d3-a456-426655440000"),
                    Arrays.asList("Alice", "Bob", "Carlos")
            );
        }catch (IOException e) {
            fail();
        }
    }

    @Test
    public void updateStatus(){
        try{
            ChatMessage text = new ChatMessage(
                    "Alice",
                    "Hello world!"
            );
            this.gameModel.writeChat(new Gson().toJson(text));
            this.gameModel.updateStatus();
        }catch(IOException e){
            fail();
        }
    }
}