package Model;

/* bisogna implementare il metodo equals per le celle */
/* attributo empty da uml, in realtà c'è già checkrefill */

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Board {

    private Cell[][] board = new Cell[9][9];

    /* 3 costruttori a seconda che si giochi in 2,3,4 */
    public Board(int nPlayer,Bag bag) {
        if (nPlayer == 2) {
            this.board = new Cell[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    Cell cell = new Cell();
                    this.board[i][j] = cell;
                }
            }
            ArrayList<Tile> tilesToInsert = bag.draw(29);
            int index = tilesToInsert.size()-1;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (
                            board[i][j] == board[1][3] || board[i][j] == board[1][4] ||
                                    board[i][j] == board[2][3] || board[i][j] == board[2][4] || board[i][j] == board[2][5] ||
                                    board[i][j] == board[3][2] || board[i][j] == board[3][3] || board[i][j] == board[3][4] || board[i][j] == board[3][5] || board[i][j] == board[3][6] || board[i][j] == board[3][7] ||
                                    board[i][j] == board[4][1] || board[i][j] == board[4][2] || board[i][j] == board[4][3] || board[i][j] == board[4][4] || board[i][j] == board[4][5] || board[i][j] == board[4][6] || board[i][j] == board[4][7] ||
                                    board[i][j] == board[5][1] || board[i][j] == board[5][2] || board[i][j] == board[5][3] || board[i][j] == board[5][4] || board[i][j] == board[5][5] || board[i][j] == board[5][6] ||
                                    board[i][j] == board[6][3] || board[i][j] == board[6][4] || board[i][j] == board[6][5] ||
                                    board[i][j] == board[7][4] || board[i][j] == board[7][5]
                    ) {
                        this.board[i][j].setStatus(true);
                        this.board[i][j].setTile(tilesToInsert.get(index));
                        index--;
                    } else{ this.board[i][j].setStatus(false);
                            this.board[i][j].setTile(null);}
                }
            }
        } else if (nPlayer == 3) {
            ArrayList<Tile> tilesToInsert = bag.draw(29);
            int index = tilesToInsert.size()-1;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (
                            board[i][j] == board[0][3] ||
                                    board[i][j] == board[1][3] || board[i][j] == board[1][4] ||
                                    board[i][j] == board[2][2] || board[i][j] == board[2][3] || board[i][j] == board[2][4] || board[i][j] == board[2][5] || board[i][j] == board[2][6] ||
                                    board[i][j] == board[3][2] || board[i][j] == board[3][3] || board[i][j] == board[3][4] || board[i][j] == board[3][5] || board[i][j] == board[3][6] || board[i][j] == board[3][7] || board[i][j] == board[3][8] ||
                                    board[i][j] == board[4][1] || board[i][j] == board[4][2] || board[i][j] == board[4][3] || board[i][j] == board[4][4] || board[i][j] == board[4][5] || board[i][j] == board[4][6] || board[i][j] == board[4][7] ||
                                    board[i][j] == board[5][0] || board[i][j] == board[5][1] || board[i][j] == board[5][2] || board[i][j] == board[5][3] || board[i][j] == board[5][4] || board[i][j] == board[5][5] || board[i][j] == board[5][6] ||
                                    board[i][j] == board[6][2] || board[i][j] == board[6][3] || board[i][j] == board[6][4] || board[i][j] == board[6][5] || board[i][j] == board[6][6] ||
                                    board[i][j] == board[7][4] || board[i][j] == board[7][5] ||
                                    board[i][j] == board[8][5]
                    ) {
                        this.board[i][j].setStatus(true);
                        this.board[i][j].setTile(tilesToInsert.get(index));
                        index--;
                    } else{ this.board[i][j].setStatus(false);
                            this.board[i][j].setTile(null);}
                }
            }
        } else if (nPlayer == 4) {
            ArrayList<Tile> tilesToInsert = bag.draw(29);
            int index = tilesToInsert.size()-1;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (
                            board[i][j] == board[0][3] || board[i][j] == board[0][4] ||
                                    board[i][j] == board[1][3] || board[i][j] == board[1][4] || board[i][j] == board[1][5] ||
                                    board[i][j] == board[2][2] || board[i][j] == board[2][3] || board[i][j] == board[2][4] || board[i][j] == board[2][5] || board[i][j] == board[2][6] ||
                                    board[i][j] == board[3][1] || board[i][j] == board[3][2] || board[i][j] == board[3][3] || board[i][j] == board[3][4] || board[i][j] == board[3][5] || board[i][j] == board[3][6] || board[i][j] == board[3][7] || board[i][j] == board[3][8] ||
                                    board[i][j] == board[4][0] || board[i][j] == board[4][1] || board[i][j] == board[4][2] || board[i][j] == board[4][3] || board[i][j] == board[4][4] || board[i][j] == board[4][5] || board[i][j] == board[4][6] || board[i][j] == board[4][7] || board[i][j] == board[4][8] ||
                                    board[i][j] == board[5][0] || board[i][j] == board[5][1] || board[i][j] == board[5][2] || board[i][j] == board[5][3] || board[i][j] == board[5][4] || board[i][j] == board[5][5] || board[i][j] == board[5][6] || board[i][j] == board[5][7] ||
                                    board[i][j] == board[6][2] || board[i][j] == board[6][3] || board[i][j] == board[6][4] || board[i][j] == board[6][5] || board[i][j] == board[6][6] ||
                                    board[i][j] == board[7][3] || board[i][j] == board[7][4] || board[i][j] == board[7][5] ||
                                    board[i][j] == board[8][4] || board[i][j] == board[8][5]
                    ) {
                        this.board[i][j].setStatus(true);
                        this.board[i][j].setTile(tilesToInsert.get(index));
                        index--;
                    } else {this.board[i][j].setStatus(false);
                            this.board[i][j].setTile(null);}
                }
            }
        }
    }

    public boolean convalidateMove(List<Coordinates> coordinates) {
        if(coordinates.size() == 1){
            // controllo solo che abbia un lato libero
            return checkSpaceTile(coordinates.get(0));
        } else if (coordinates.size() == 2) {
            return checkAdjacent(coordinates.get(0),coordinates.get(1)) && checkSpaceTile(coordinates.get(0),coordinates.get(1));
        } else if (coordinates.size() == 3) {
            return checkAdjacent(coordinates.get(0),coordinates.get(1),coordinates.get(2)) && checkSpaceTile(coordinates.get(0),coordinates.get(1),coordinates.get(2));
        } else {
            /* exception, troppe tiles selezionate */
            return false;
        }
    }

    public ArrayList<Tile> getTiles(ArrayList<Coordinates> coordinates){
        ArrayList<Tile> result = new ArrayList<Tile>();
        for (Coordinates coordinate : coordinates) {
            Cell selected = getCell(coordinate);
            try {
                result.add(selected.getTile());
                selected.setTile(null);
            }catch (NullPointerException nullPointerException){return null;}
        }
        return result;
    }

    public void setTiles(Bag bag,int n){
        ArrayList<Tile> toDeploy = bag.draw(n);
        int index = n-1;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(this.board[i][j].getStatus()){
                    if(this.board[i][j].getTile()==null){
                        this.board[i][j].setTile(toDeploy.get(index));
                        index--;
                    }
                }
            }
        }
    }

    public Cell getCell(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        return board[x][y];
    }

    public boolean checkRefill(){
        boolean lonelyTile = false;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(this.board[i][j].getStatus()){
                    if( !((this.board[i][j].getTile())==null) ){
                        Coordinates c1 = new Coordinates(i,j);
                        lonelyTile = checkLonelyTile(c1);
                    }
                    if(lonelyTile) return true;
                }
            }
        }
        return false;
    }

    private boolean checkAdjacent(Coordinates c1,Coordinates c2, Coordinates c3){
        int x1 = c1.getX();
        int y1 = c1.getY();
        int x2 = c2.getX();
        int y2 = c2.getY();
        int x3 = c3.getX();
        int y3 = c3.getY();
        return x1 == x2 && x2 == x3 && abs(y1 - y2) == 1 && abs(y2 - y3) == 1 ||
                y1 == y2 && y2 == y3 && abs(x1 - x2) == 1 && abs(x2 - x3) == 1;
    }
    private boolean checkAdjacent(Coordinates c1, Coordinates c2){
        int x1 = c1.getX();
        int y1 = c1.getY();
        int x2 = c2.getX();
        int y2 = c2.getY();
        return x1 - x2 == 0 && abs(y1 - y2) == 1 ||
                y1 - y2 == 0 && abs(x1 - x2) == 1;
    }
    // checkSpaceTile : se c'è almeno un lato libero ritorna true

    private boolean checkSpaceTile(Coordinates c1){
        int x1 = c1.getX();
        int y1 = c1.getY();
        // controllo solo che abbia un lato libero
        Coordinates check = new Coordinates(x1+1,y1);
        if(
                !getCell(check).getStatus() || getCell(check).getStatus() && getCell(check).getTile()==(null)
        ){return true;}
        Coordinates check1 = new Coordinates(x1-1,y1);
        if(
                !getCell(check1).getStatus() || getCell(check1).getStatus() && getCell(check1).getTile()==(null)
        ){return true;}
        Coordinates check2 = new Coordinates(x1,y1+1);
        if(
                !getCell(check2).getStatus() || getCell(check2).getStatus() && getCell(check2).getTile()==(null)
        ){return true;}
        Coordinates check3 = new Coordinates(x1,y1-1);
        return !getCell(check3).getStatus() || getCell(check3).getStatus() && getCell(check3).getTile() == (null);
    }
    private boolean checkSpaceTile(Coordinates c1, Coordinates c2){
        boolean tile1 = checkSpaceTile(c1);
        boolean tile2 = checkSpaceTile(c2);
        return tile1 && tile2;
    }
    private boolean checkSpaceTile(Coordinates c1, Coordinates c2, Coordinates c3){
        boolean tile12 = checkSpaceTile(c1,c2);
        boolean tile3 = checkSpaceTile(c3);
        return tile12  && tile3;
    }

    private boolean checkLonelyTile(Coordinates c1) {
        int x1 = c1.getX();
        int y1 = c1.getY();
        int refill = 0;
        // controllo solo che abbia un lato libero
        Coordinates check = new Coordinates(x1+1,y1);
        if(
                !getCell(check).getStatus() || getCell(check).getStatus() && getCell(check).getTile()==(null)
        ){refill++;}
        Coordinates check1 = new Coordinates(x1-1,y1);
        if(
                !getCell(check1).getStatus() || getCell(check1).getStatus() && getCell(check1).getTile()==(null)
        ){refill++;}
        Coordinates check2 = new Coordinates(x1,y1+1);
        if(
                !getCell(check2).getStatus() || getCell(check2).getStatus() && getCell(check2).getTile()==(null)
        ){refill++;}
        Coordinates check3 = new Coordinates(x1,y1-1);
        if( !getCell(check3).getStatus() || getCell(check3).getStatus() && getCell(check3).getTile() == (null)){refill++;}
        return refill == 4;

    }
}