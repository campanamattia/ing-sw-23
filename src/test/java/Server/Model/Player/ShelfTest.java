package Server.Model.Player;

import Enumeration.Color;
import Exception.Player.ColumnNotValidException;
import Server.Model.Player.Shelf;
import Utils.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ShelfTest {

    @Test
    void insert() throws ColumnNotValidException{

        Shelf TestShelf = new Shelf();
        List<Tile> InsTiles = new ArrayList<Tile>();


        InsTiles.add(new Tile(Color.PINK));
        InsTiles.add(new Tile(Color.CYAN));
        InsTiles.add(new Tile(Color.WHITE));


        int tmp = InsTiles.size();

        //test if tiles are correctly insert in the arraylist
        System.out.println(tmp);

        TestShelf.insert(0, InsTiles);

        //test for the correct execution of the method insert
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                if (TestShelf.getTile(i,j) != null) {
                    System.out.println(TestShelf.getTile(i, j).getTileColor() + " ");
                }
            }

        }

    }

    @Test
    void full() {

        //test class with the same methods of shelf but with different constructor
        class FullShelf {
            private Tile[][] myShelf;
            public FullShelf(){
                this.myShelf = new Tile[6][5];
                Tile Insert = new Tile(Color.WHITE);
                for (int i=0; i<6; i++){
                    for (int j=0; j<5; j++) {
                        this.myShelf[i][j] = Insert;
                    }
                }
            }

            public void insert(int n, List<Tile> tiles) throws ColumnNotValidException{

                int tmp = tiles.size();
                int full = 0;

                if(this.myShelf[tiles.size()-1][n] != null)
                    throw new ColumnNotValidException(n);
                for(int i=5; i>0 ; i-- ){
                    if(this.myShelf[i][n] == null){
                        if(full<tmp) {
                            this.myShelf[i][n] = tiles.get(full);
                            full++;
                        }
                    }
                }
            }

            public boolean full(){
                for(int i=0; i<5; i++)
                    if(this.getTile(0, i ) == null) {
                        return false;
                    }
                return true;
            }

            public int checkMaxTiles(){
                int count = 0;
                int max = 0;

                for(int j=0; j<5; j++){
                    for(int i=0; i<6; i++){
                        if(this.myShelf[i][j] == null )
                            count++;
                        else break;
                    }
                    if(count > max) max = count;
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
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                if (TestShelf.getTile(i,j) != null) {
                    System.out.println(TestShelf.getTile(i, j).getTileColor() + " ");
                }
            }

        }

        boolean i = TestShelf.full();

        //full should give true
        System.out.println(i);


    }


    @Test
    void checkMaxTiles() {

        Shelf Max1 = new Shelf();
        //test if the default checkMaxTiles give 6
        int T1 = Max1.checkMaxTiles();
        System.out.println(T1);

        //create a shelf class with specific tiles in specific coordinates
        class Shelfmax {
            private Tile[][] myShelf;

            public Shelfmax(int n) {
                this.myShelf = new Tile[6][5];
                Tile TestTile = new Tile(Color.WHITE);
                switch (n){
                    case 1: for(int i = 0; i<5; i++){
                        this.myShelf[0][i] = TestTile;
                        }
                    break;
                    case 2: for(int i = 0; i<5; i++){
                        this.myShelf[0][i] = TestTile;
                        this.myShelf[1][i] = TestTile;
                    }
                    break;
                    case 3: for(int i = 0; i<4; i++){
                        this.myShelf[0][i] = TestTile;
                    }
                    break;

                }
            }

            public void insert(int n, List<Tile> tiles) throws ColumnNotValidException{

                int tmp = tiles.size();
                int full = 0;

                if(this.myShelf[tiles.size()-1][n] != null)
                    throw new ColumnNotValidException(n);
                for(int i=5; i>0 ; i-- ){
                    if(this.myShelf[i][n] == null){
                        if(full<tmp) {
                            this.myShelf[i][n] = tiles.get(full);
                            full++;
                        }
                    }
                }
            }

            public boolean full(){
                for(int i=0; i<5; i++)
                    if(this.myShelf[0][i] == null)
                        return false;
                return true;
            }

            public int checkMaxTiles(){
                int max = 0;

                for(int j=0; j<5; j++){
                    int count = 0;
                    for(int i=5; i>=0; i--){
                        if(this.myShelf[i][j] == null )
                            count++;
                        else break;
                    }
                    if(count > max) max = count;
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

        //different limit test
        //with first line filled
        Shelfmax Max2 = new Shelfmax(1);
        int T2 = Max2.checkMaxTiles();
        System.out.println(T2);

        //first two lines filled
        Shelfmax Max3 = new Shelfmax(2);
        int T3 = Max3.checkMaxTiles();
        System.out.println(T3);

        //first line filled except for last column
        Shelfmax Max4 = new Shelfmax(3);
        int T4 = Max4.checkMaxTiles();
        System.out.println(T4);





    }
}