package Server.Model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    GameModel gameModel;
    @BeforeEach
    public void setUp() {
        try{
            gameModel = new GameModel(UUID.randomUUID(), Arrays.asList("Alice", "Bob", "Carlos"));
        }catch (IOException e) {
            fail();
        }
    }

    @Test
    public void updateStatus(){
        try{
            this.gameModel.updateStatus();
        }catch(IOException e){
            fail();
        }
    }
}