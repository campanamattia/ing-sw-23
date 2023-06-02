package Server.Model.LivingRoom;

import Exception.Board.CantRefillBoardException;
import Exception.Board.NoValidMoveException;
import Exception.Board.NullTileException;
import Utils.Cell;
import Utils.Coordinates;
import Utils.Tile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Class Board, it contains all the method to for player's move in the game.
 */
public class Board {
    private final Cell[][] board;
    private int tilesTaken;
    private final int matrix_size;
    private boolean lastRound;          //CHECK THIS LINE

    /**
     * Class constructor.
     * @param board_json it contains the scheme of the board.
     * @param bag bag of tiles created at the start of the game.
     */
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

    /**
     * Check if the move selected by the player is playable.
     * @param coordinates coordinates of the tiles that the player want to take.
     * @throws NoValidMoveException exception thrown if the move isn't valid.
     */
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

    /**
     * Remove the tiles taken off the board.
     * @param coordinates coordinate of the tiles that
     * @return list of the taken tiles.
     * @throws NullTileException exception if someone has tried to take a tile but on the board there isn't that tile.
     */
    public List<Tile> getTiles(List<Coordinates> coordinates) throws NullTileException{
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

    /**
     * Deploy n tiles on the board in the empty spaces.
     * @param bag it contains all the tiles left for the game.
     * @param n number of tiles that are going to be deployed on the board.
     */
    public void setTiles(Bag bag,int n){
        ArrayList<Tile> toDeploy = bag.draw(n);
        int index = n-1;
        for(int i=0;i<matrix_size;i++){
            for(int j=0;j<matrix_size;j++){
                if(this.board[i][j].getStatus()){
                    if(this.board[i][j].getTile()==null){
                        this.board[i][j].setTile(toDeploy.get(index));
                        index--;
                    }
                }
            }
        }
    }

    /**
     * Check if there aren't playable move left on the board.
     * @param bag it contains all the tiles left for the game.
     * @throws CantRefillBoardException exception thrown if there aren't enough tiles in the bag to refill the board.
     */
    public void checkRefill(Bag bag) throws CantRefillBoardException{
        boolean lonelyTile = true;
        outerloop:
        for(int i=0;i<matrix_size;i++){
            for(int j=0;j<matrix_size;j++){
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


    /**
     * List of useful values of the board.
     * @param json it contains all board's information.
     * @return list of useful values of the board.
     */
    private static  List<Integer> getValueList (JsonArray json){
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <=json.size(); i++)
            list.add(json.get(json.size()-i).getAsInt());
        return list;
    }

    /**
     *Standard get method for Cell class
     * @param coordinates coordinates that indicate the cell that want to be taken.
     * @return cell selected.
     */
    private Cell getCell(Coordinates coordinates) {
        int x = coordinates.x();
        int y = coordinates.y();
        return board[x][y];
    }

    /**
     * Overload of checkAdjacent.
     * @param c1 first tile.
     * @param c2 second tile.
     * @param c3 third tile.
     * @return true if yes, false if no.
     */
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

    /**
     * Check if the selected tiles are adjacent.
     * @param c1 first tile.
     * @param c2 second tile.
     * @return true if yes, false if no.
     */
    private boolean checkAdjacent(Coordinates c1, Coordinates c2){
        int x1 = c1.x();
        int y1 = c1.y();
        int x2 = c2.x();
        int y2 = c2.y();
        return x1 - x2 == 0 && abs(y1 - y2) == 1 ||
                y1 - y2 == 0 && abs(x1 - x2) == 1;
    }

    /**
     * Check if there is at least a free space around the tile.
     * @param c1 coordinates of the tile.
     * @return true if yes, false if no.
     */
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

    /**
     * Overload of checkSpaceTile.
     * @param c1 first tile.
     * @param c2 second tile.
     * @return true if yes, false if no.
     */
    private boolean checkSpaceTile(Coordinates c1, Coordinates c2){
        boolean tile1 = checkSpaceTile(c1);
        boolean tile2 = checkSpaceTile(c2);
        return tile1 && tile2;
    }

    /**
     * Overload of checkSpaceTile.
     * @param c1 first tile.
     * @param c2 second tile.
     * @param c3 third tile.
     * @return true if yes, false if no.
     */
    private boolean checkSpaceTile(Coordinates c1, Coordinates c2, Coordinates c3){
        boolean tile12 = checkSpaceTile(c1,c2);
        boolean tile3 = checkSpaceTile(c3);
        return tile12  && tile3;
    }

    /**
     * Check if the tile hasn't any other tile around agreeing with rules of the game.
     * @param c1 coordinate of tile selected.
     * @return true if yes, false if no.
     */
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

    /**
     * Check if the tile selected is on the side of the board.
     * @param c1 coordinate of tile selected.
     * @return true if yes, false if no.
     */
    private boolean tileOnEdge(Coordinates c1){
        int x1 = c1.x();
        int y1 = c1.y();
        return x1==0 || y1==0 || x1==matrix_size-1 || y1==matrix_size-1;
    }

    /**
     * Method useful just for tests.
     * @param tilesTaken tile removed from the board during the entire game.
     */
    public void setTilesTaken(int tilesTaken) {
        this.tilesTaken = tilesTaken;
    }

    public Cell[][] getBoard() {
        return board;
    }
}