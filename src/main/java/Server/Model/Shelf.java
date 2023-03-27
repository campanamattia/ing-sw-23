package Server.Model;

import java.util.List;

public class Shelf {
    private Tile[][] myShelf;

    public Shelf() {
        this.myShelf = new Tile[6][5];
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean insert(int n, List<Tile> tiles){
        int tmp = tiles.size();

        for(int i=0; i<tmp; i++){
            if(this.myShelf[6-i][n] != null ) return false;
        }

        for(int j=0; j<6 ; j++ ){
            if(this.myShelf[j][n] == null){
                this.myShelf[j][n] = tiles.get(1);
            }
        }

        return true;
    }

    public boolean full(){
        for(int i=0; i<6; i++){
            for(int j=0;j<5;j++){
                if(myShelf[i][j] == null) return false;
            }
        }
        return true;
    }

    public int checkMaxTiles(){
        int count = 0;
        int max = 0;

        for(int j=0; j<5; j++){
            for(int i=0; i<6; i++){
                if(this.myShelf[i][j] == null ) count++;
            }
            if(count > max) max = count;
        }
        return max;
    }

    public Tile[][] getShelf(){
        return myShelf;
    }



}
