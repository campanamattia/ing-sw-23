package Server.Model.LivingRoom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag = new Bag();

    @Test
    // Testing before drawing
    void getLastTiles() {
        assertDoesNotThrow(() -> {
            this.bag.getLastTiles();
        });
    }

    @Test
    // Draw n tiles
    void draw() {
        assertDoesNotThrow(() -> {
            this.bag.draw(22);
            System.out.println(this.bag.getLastTiles());
        });
    }
}