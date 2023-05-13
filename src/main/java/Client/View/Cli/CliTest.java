package Client.View.Cli;

import Enumeration.Color;
import Server.Model.Shelf;
import Utils.Cell;
import Utils.Tile;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Client.View.Cli.Cli.clearCLI;

public class CliTest {

    public static void main(String[] arg) {
        Cli cli = new Cli();
        Cell [][] board = new Cell[9][9];

        Tile t1 = new Tile(Color.BLUE);
        Tile t2 = new Tile(Color.GREEN);

        List <Tile> tileList = new ArrayList<>();
        tileList.add(t1);
        tileList.add(t2);

        board[0][0] = new Cell();
        board[0][1] = new Cell();
        board[0][2] = new Cell();
        board[0][3] = new Cell(new Tile(Color.CYAN));
        board[0][4] = new Cell();
        board[0][5] = new Cell();
        board[0][6] = new Cell();
        board[0][7] = new Cell();
        board[0][8] = new Cell();

        board[1][0] = new Cell();
        board[1][1] = new Cell();
        board[1][2] = new Cell();
        board[1][3] = new Cell(new Tile(Color.BLUE));
        board[1][4] = new Cell(new Tile(Color.BLUE));
        board[1][5] = new Cell();
        board[1][6] = new Cell();
        board[1][7] = new Cell();
        board[1][8] = new Cell();

        board[2][0] = new Cell();
        board[2][1] = new Cell();
        board[2][2] = new Cell();
        board[2][3] = new Cell(new Tile(Color.BLUE));
        board[2][4] = new Cell(new Tile(Color.WHITE));
        board[2][5] = new Cell(new Tile(Color.YELLOW));
        board[2][6] = new Cell();
        board[2][7] = new Cell();
        board[2][8] = new Cell();

        board[3][0] = new Cell();
        board[3][1] = new Cell();
        board[3][2] = new Cell(new Tile(Color.GREEN));
        board[3][3] = new Cell(new Tile(Color.PINK));
        board[3][4] = new Cell(new Tile(Color.BLUE));
        board[3][5] = new Cell(new Tile(Color.WHITE));
        board[3][6] = new Cell(new Tile(Color.BLUE));
        board[3][7] = new Cell(new Tile(Color.CYAN));
        board[3][8] = new Cell();

        board[4][0] = new Cell(new Tile(Color.WHITE));
        board[4][1] = new Cell(new Tile(Color.CYAN));
        board[4][2] = new Cell(new Tile(Color.BLUE));
        board[4][3] = new Cell(new Tile(Color.WHITE));
        board[4][4] = new Cell(new Tile(Color.YELLOW));
        board[4][5] = new Cell(new Tile(Color.BLUE));
        board[4][6] = new Cell(new Tile(Color.YELLOW));
        board[4][7] = new Cell(new Tile(Color.GREEN));
        board[4][8] = new Cell();

        board[5][0] = new Cell();
        board[5][1] = new Cell(new Tile(Color.BLUE));
        board[5][2] = new Cell(new Tile(Color.WHITE));
        board[5][3] = new Cell(new Tile(Color.BLUE));
        board[5][4] = new Cell(new Tile(Color.YELLOW));
        board[5][5] = new Cell(new Tile(Color.PINK));
        board[5][6] = new Cell(new Tile(Color.BLUE));
        board[5][7] = new Cell();
        board[5][8] = new Cell();

        board[6][0] = new Cell();
        board[6][1] = new Cell();
        board[6][2] = new Cell();
        board[6][3] = new Cell(new Tile(Color.BLUE));
        board[6][4] = new Cell(new Tile(Color.PINK));
        board[6][5] = new Cell(new Tile(Color.BLUE));
        board[6][6] = new Cell();
        board[6][7] = new Cell();
        board[6][8] = new Cell();

        board[7][0] = new Cell();
        board[7][1] = new Cell();
        board[7][2] = new Cell();
        board[7][3] = new Cell();
        board[7][4] = new Cell(new Tile(Color.WHITE));
        board[7][5] = new Cell(new Tile(Color.GREEN));
        board[7][6] = new Cell();
        board[7][7] = new Cell();
        board[7][8] = new Cell();

        board[8][0] = new Cell();
        board[8][1] = new Cell();
        board[8][2] = new Cell();
        board[8][3] = new Cell();
        board[8][4] = new Cell();
        board[8][5] = new Cell();
        board[8][6] = new Cell();
        board[8][7] = new Cell();
        board[8][8] = new Cell();

        Tile [][] shelf1 = new Tile[6][6];

        Tile [][] shelf2 = new Tile[6][6];
        Tile [][] shelf3 = new Tile[6][6];
        Tile [][] shelf4 = new Tile[6][6];

        HashMap<String, Tile[][]> shelves = new HashMap<>();

        shelves.put("pasquale",shelf1);
        shelves.put("giovanni",shelf2);
        shelves.put("antonio",shelf3);
        shelves.put("francesco",shelf4);

        cli.showTitle();

        cli.showBoard();

        cli.showShelves();

        cli.showTile(tileList);



    }
}
