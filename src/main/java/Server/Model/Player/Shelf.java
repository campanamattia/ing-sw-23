package Server.Model.Player;

import Exception.Player.ColumnNotValidException;
import Utils.Tile;

import java.util.List;

public class Shelf {
    private final Tile[][] myShelf;

    public Shelf() {
        this.myShelf = new Tile[6][5];
    }

    public void insert(int n, List<Tile> tiles) throws ColumnNotValidException {
        if (n < 0 || n > 4 || myShelf[tiles.size() - 1][n] != null)
            throw new ColumnNotValidException(n);
        for (int i = 5; i >=0; i--) {
            if (myShelf[i][n] == null)
                myShelf[i][n] = tiles.remove(0);
            if (tiles.isEmpty())
                break;
        }
    }

    public boolean full(){
        for(int i=0; i<5; i++)
            if(this.myShelf[0][i] == null)
                return false;
        return true;
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

    public int numberRows () {
        return myShelf.length;
    }

    public int numberColumns() {
        return myShelf[0].length;
    }

    public void placeTile( Tile tile, int row, int column) {
        myShelf[row][column] = tile;
    }
}
