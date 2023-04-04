package Server.Controller;

import Exception.*;
import Exception.Board.NullTileException;
import Exception.Player.ColumnNotValidException;
import Interface.CMD;
import Server.Model.*;

import java.util.*;

public class PlayerAction implements CMD {
    private final GameModel game;

    public PlayerAction(GameModel game) {
        this.game = game;
    }

    @Override
    public List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException {
        try{
            return game.selectedTiles(coordinates);
        }catch (BoardException e) {
            System.out.println(e.toString());
            throw e;
        }
    }

    @Override
    public void insertTiles(List<Integer> sort, List<Tile> tiles, int column) throws PlayerException {
        try{
            game.insertTiles(sort, tiles, column);
        }catch (ColumnNotValidException e){
            System.out.println(e.toString());
            throw e;
        }
    }

    @Override
    public void writeChat(String message) {
        game.writeChat(message);
    }
}
