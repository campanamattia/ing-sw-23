package Server.Model.Player;

import Enumeration.Color;
import Exception.Player.ColumnNotValidException;
import Utils.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings("ALL")
class ShelfTest {
    static Shelf TestShelf = new Shelf();


    @BeforeEach
    void setUp() {
        TestShelf = new Shelf();
    }

    @Test
    void insert() {

        assertDoesNotThrow(() -> {
            List<Tile> InsTiles = new ArrayList<>();


            InsTiles.add(new Tile(Color.PINK));
            InsTiles.add(new Tile(Color.CYAN));
            InsTiles.add(new Tile(Color.WHITE));


            int tmp = InsTiles.size();

            //test if tiles are correctly insert in the arraylist
            System.out.println(tmp);

            TestShelf.insert(0, InsTiles);

            //test for the correct execution of the method insert
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (TestShelf.getTile(i, j) != null) {
                        System.out.println(TestShelf.getTile(i, j).color() + " ");
                    }
                }

            }
        });
    }

    @Test
    void full() {

        //test class with the same methods of shelf but with different constructor
        class FullShelf {
            private final Tile[][] myShelf;

            public FullShelf() {
                this.myShelf = new Tile[6][5];
                Tile Insert = new Tile(Color.WHITE);
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        this.myShelf[i][j] = Insert;
                    }
                }
            }

            public void insert(int n, List<Tile> tiles) throws ColumnNotValidException {

                int tmp = tiles.size();
                int full = 0;

                if (this.myShelf[tiles.size() - 1][n] != null) throw new ColumnNotValidException(n);
                for (int i = 5; i > 0; i--) {
                    if (this.myShelf[i][n] == null) {
                        if (full < tmp) {
                            this.myShelf[i][n] = tiles.get(full);
                            full++;
                        }
                    }
                }
            }

            public boolean full() {
                for (int i = 0; i < 5; i++)
                    if (this.getTile(0, i) == null) {
                        return false;
                    }
                return true;
            }

            public int checkMaxTiles() {
                int count = 0;
                int max = 0;

                for (int j = 0; j < 5; j++) {
                    for (int i = 0; i < 6; i++) {
                        if (this.myShelf[i][j] == null) count++;
                        else break;
                    }
                    if (count > max) max = count;
                }
                return max;
            }

            public Tile getTile(int i, int j) {
                return myShelf[i][j];
            }

            public Tile[][] getMyShelf() {
                return myShelf;
            }

            public int checkEndGame() {
                return 0;
            }
        }

        FullShelf TestShelf = new FullShelf();

        //tiles insert correctly
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (TestShelf.getTile(i, j) != null) {
                    System.out.println(TestShelf.getTile(i, j).color() + " ");
                }
            }

        }

        boolean i = TestShelf.full();

        //full should give true
        System.out.println(i);
    }

    @Test
    void maxTiles() {
        for (int k = 0; k < TestShelf.numberRows(); k++) {
            clear();
            for (int i = TestShelf.numberRows() - 1; i >= k; i--) {
                for (int j = TestShelf.numberColumns() - 1; j >= 0; j--) {
                    TestShelf.placeTile(new Tile(Color.PINK), i, j);
                }
            }
            assertEquals(k, TestShelf.maxTiles());
        }

        for (int k = 0; k < TestShelf.numberColumns(); k++) {
            clear();
            for (int i = TestShelf.numberRows() - 1; i >= 0; i--) {
                for (int j = TestShelf.numberColumns() - 1; j >= k; j--) {
                    TestShelf.placeTile(new Tile(Color.PINK), i, j);
                }
            }
            assertEquals((k == 0) ? 0 : 6, TestShelf.maxTiles());
        }

        clear();
        assertEquals(6, TestShelf.maxTiles());
        
    }

    private void clear() {
        for (int i = TestShelf.numberRows() - 1; i >= 0; i--) {
            for (int j = TestShelf.numberColumns() - 1; j >= 0; j--) {
                TestShelf.placeTile(null, i, j);
            }
        }
    }
}