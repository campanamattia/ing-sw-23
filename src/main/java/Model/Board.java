package Model;

/* bisogna implementare il metodo equals per le celle */
/* attributo empty da uml, in realtà c'è già checkrefill */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.nextUp;

public class Board {

    private Cell[][] board = new Cell[9][9];
    private int tilesTaken;
    private final int matrix_size;

    /* 3 costruttori a seconda che si giochi in 2,3,4 */
    public Board(JsonObject board_json, Bag bag) {
        this.matrix_size = board_json.get("matrix.size").getAsInt();
        int board_size = board_json.get("board.size").getAsInt();
        List<Integer> cell_value = getValueList(board_json.getAsJsonArray("cell.value"));
        List<Tile> toDeploy = bag.draw(board_size);
        int rowIndex=0;
        int columnIndex=0;
        for (Integer integer : cell_value) {
            if(cell_value.get(integer) == 1) {
                this.board[rowIndex][columnIndex].setStatus(true);
                this.board[rowIndex][columnIndex].setTile(toDeploy.remove(0));
            }
            columnIndex++;
            if (columnIndex == matrix_size-1) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }
    private static  List<Integer> getValueList (JsonArray json){
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <=json.size(); i++)
            list.add(json.get(json.size()-i).getAsInt());
        return list;
    }

    public void convalidateMove(List<Coordinates> coordinates) throws NoValidMoveException {
        boolean validMove = false;
        if(coordinates.size() == 1){
            // controllo solo che abbia un lato libero
            validMove = checkSpaceTile(coordinates.get(0));
        } else if (coordinates.size() == 2) {
            validMove = checkAdjacent(coordinates.get(0),coordinates.get(1)) && checkSpaceTile(coordinates.get(0),coordinates.get(1));
        } else if (coordinates.size() == 3) {
            validMove = checkAdjacent(coordinates.get(0),coordinates.get(1),coordinates.get(2)) && checkSpaceTile(coordinates.get(0),coordinates.get(1),coordinates.get(2));
        }
        // da sistemare con le eccezioni
        if(!validMove){
            throw NoValidMoveException("Selezione tessere non valida");
        }
    }

    public ArrayList<Tile> getTiles(ArrayList<Coordinates> coordinates) throws NullTileException{
        ArrayList<Tile> result = new ArrayList<Tile>();
        for (Coordinates coordinate : coordinates) {
            Cell selected = getCell(coordinate);
            if( selected.getTile() != null) {
                result.add(selected.getTile());
                selected.setTile(null);
                tilesTaken++;
            }else{
                throw NullTileException(coordinate);
            }

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

    public void checkRefill(Bag bag) throws CantRefillBoardException{
        boolean lonelyTile = true;
        outerloop:
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(this.board[i][j].getStatus()){
                    if( !((this.board[i][j].getTile())==null) ){
                        Coordinates c1 = new Coordinates(i,j);
                        lonelyTile = checkLonelyTile(c1);
                    }
                    if(!lonelyTile) break outerloop;
                }
            }
        }
        if(lonelyTile){
            if(tilesTaken > bag.getLastTiles()){
                throw new CantRefillBoardException ();
            }
        }
    }

    private Cell getCell(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        return board[x][y];
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
                tileOnBoard(c1) || !getCell(check).getStatus() || ( getCell(check).getStatus() && getCell(check).getTile()==(null) )
        ){return true;}
        Coordinates check1 = new Coordinates(x1-1,y1);
        if(
                tileOnBoard(c1) || !getCell(check1).getStatus() || ( getCell(check1).getStatus() && getCell(check1).getTile()==(null) )
        ){return true;}
        Coordinates check2 = new Coordinates(x1,y1+1);
        if(
                tileOnBoard(c1) || !getCell(check2).getStatus() || ( getCell(check2).getStatus() && getCell(check2).getTile()==(null) )
        ){return true;}
        Coordinates check3 = new Coordinates(x1,y1-1);
        return tileOnBoard(c1) || !getCell(check3).getStatus() || ( getCell(check3).getStatus() && getCell(check3).getTile() == (null) );
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

    private boolean tileOnBoard(Coordinates c1){
        int x1 = c1.getX();
        int y1 = c1.getY();
        return x1 == matrix_size - 1 || y1 == matrix_size - 1;
    }
}