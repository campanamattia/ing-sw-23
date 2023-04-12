package Server.Model;

import Server.Exception.EndingPhase;
import Server.Exception.Player.*;

import java.util.List;

public class Shelf {
    private Tile[][] myShelf;

    public Shelf() {
        this.myShelf = new Tile[6][5];
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
