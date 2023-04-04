package Server.Model;

import Server.Exception.Player.*;

import java.util.List;

public class Shelf {
    private Tile[][] myShelf;

    public Shelf() {
        this.myShelf = new Tile[6][5];
    }

    public void insert(int n, List<Tile> tiles) throws ColumnNotValidException{
        int tmp = tiles.size();

        if(this.myShelf[tiles.size()-1][n] != null) 
            throw new ColumnNotValidException(n);
        for(int i=5; i>=0 ; i-- ){
            if(this.myShelf[i][n] == null){
                this.myShelf[i][n] = tiles.get(0); // I think this is tile.get(i)
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


    //method create only for create shelf for testing commonGoal
    public void placeTile(Tile tile, int i, int j) {
        myShelf[i][j] = tile;
    }
}
