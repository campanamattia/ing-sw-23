package Server.Model;

import Exception.Board.CantRefillBoardException;
import Exception.Board.NoValidMoveException;
import Exception.Board.NullTileException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.nextUp;

public class Board {

    private Cell[][] board;
    private int tilesTaken;
    private final int matrix_size;

    public Board(JsonObject board_json, Bag bag) {
        this.matrix_size = board_json.get("matrix.size").getAsInt();
        this.board = new Cell[matrix_size][matrix_size];
        for(int i=0;i<matrix_size;i++){
            for(int j=0;j<matrix_size;j++){
                this.board[i][j] = new Cell();
            }
        }
        int board_size = board_json.get("board.size").getAsInt();
        List<Integer> cell_value = getValueList(board_json.getAsJsonArray("cell.value"));
        List<Tile> toDeploy = bag.draw(board_size);
        int k=0;
        for (int i=0;i<matrix_size;i++) {
            for(int j=0;j<matrix_size;j++){
                if(cell_value.get(k) == 1) {
                    this.board[i][j].setStatus(true);
                    this.board[i][j].setTile(toDeploy.remove(0));
                }else{
                    this.board[i][j].setStatus(false);
                    this.board[i][j].setTile(null);
                }
                k++;
            }
        }
    }
    public void convalidateMove(@NotNull List<Coordinates> coordinates) throws NoValidMoveException {
        boolean validMove = false;
        if(coordinates.size() == 1){
            validMove = checkSpaceTile(coordinates.get(0));
        } else if (coordinates.size() == 2) {
            validMove = checkAdjacent(coordinates.get(0),coordinates.get(1)) && checkSpaceTile(coordinates.get(0),coordinates.get(1));
        } else if (coordinates.size() == 3) {
            validMove = checkAdjacent(coordinates.get(0),coordinates.get(1),coordinates.get(2)) && checkSpaceTile(coordinates.get(0),coordinates.get(1),coordinates.get(2));
        }
        if(!validMove){
            throw new NoValidMoveException();
        }
    }

    public List<Tile> getTiles(List<Coordinates> coordinates) throws NullTileException {
        List<Tile> result = new ArrayList<Tile>();
        for (Coordinates coordinate : coordinates) {
            Cell selected = getCell(coordinate);
            if( selected.getTile() != null) {
                result.add(selected.getTile());
                selected.setTile(null);
                tilesTaken++;
            }else{
                throw new NullTileException(coordinate);
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

    public void checkRefill(Bag bag) throws CantRefillBoardException {
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
            }else setTiles(bag,tilesTaken);
        }
    }


    private static  List<Integer> getValueList (JsonArray json){
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <=json.size(); i++)
            list.add(json.get(json.size()-i).getAsInt());
        return list;
    }

    private Cell getCell(Coordinates coordinates) {
        int x = coordinates.x();
        int y = coordinates.y();
        return board[x][y];
    }

    private boolean checkAdjacent(Coordinates c1,Coordinates c2, Coordinates c3){
        int x1 = c1.x();
        int y1 = c1.y();
        int x2 = c2.x();
        int y2 = c2.y();
        int x3 = c3.x();
        int y3 = c3.y();
        return x1 == x2 && x2 == x3 && abs(y1 - y2) == 1 && abs(y2 - y3) == 1 ||
                y1 == y2 && y2 == y3 && abs(x1 - x2) == 1 && abs(x2 - x3) == 1;
    }
    private boolean checkAdjacent(Coordinates c1, Coordinates c2){
        int x1 = c1.x();
        int y1 = c1.y();
        int x2 = c2.x();
        int y2 = c2.y();
        return x1 - x2 == 0 && abs(y1 - y2) == 1 ||
                y1 - y2 == 0 && abs(x1 - x2) == 1;
    }

    private boolean checkSpaceTile(Coordinates c1){
        int x1 = c1.x();
        int y1 = c1.y();
        if ( !this.board[x1][y1].getStatus() ) return false;
        if(tileOnEdge(c1)) return true;
        Coordinates check = new Coordinates(x1+1,y1);
        if(
                !getCell(check).getStatus() || ( getCell(check).getStatus() && getCell(check).getTile()==(null) )
        ){return true;}
        Coordinates check1 = new Coordinates(x1-1,y1);
        if(
                !getCell(check1).getStatus() || ( getCell(check1).getStatus() && getCell(check1).getTile()==(null) )
        ){return true;}
        Coordinates check2 = new Coordinates(x1,y1+1);
        if(
                !getCell(check2).getStatus() || ( getCell(check2).getStatus() && getCell(check2).getTile()==(null) )
        ){return true;}
        Coordinates check3 = new Coordinates(x1,y1-1);
        return !getCell(check3).getStatus() || ( getCell(check3).getStatus() && getCell(check3).getTile() == (null) );
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
        int x1 = c1.x();
        int y1 = c1.y();
        int refill = 0;
        Coordinates check = new Coordinates(x1+1,y1);
        if(x1 != matrix_size-1){
            if (!getCell(check).getStatus() || getCell(check).getStatus() && getCell(check).getTile()==(null))
            {refill++;}
        }else{
            refill++;
        }
        Coordinates check1 = new Coordinates(x1-1,y1);
        if(x1 != 0){
            if (!getCell(check1).getStatus() || getCell(check1).getStatus() && getCell(check1).getTile()==(null))
            {refill++;}
        }else{
            refill++;
        }
        Coordinates check2 = new Coordinates(x1,y1+1);
        if(y1 != matrix_size-1){
            if (!getCell(check2).getStatus() || getCell(check2).getStatus() && getCell(check2).getTile()==(null))
            {refill++;}
        }else{
            refill++;
        }
        Coordinates check3 = new Coordinates(x1,y1-1);
        if(x1 != 0){
            if (!getCell(check1).getStatus() || getCell(check1).getStatus() && getCell(check1).getTile()==(null))
            {refill++;}
        }else{
            refill++;
        }
        return refill == 4;
    }

    private boolean tileOnEdge(Coordinates c1){
        int x1 = c1.x();
        int y1 = c1.y();
        return x1==0 || y1==0 || x1==matrix_size-1 || y1==matrix_size-1;
    }

    // method useful only for test
    public void setTilesTaken(int tilesTaken) {
        this.tilesTaken = tilesTaken;
    }
}