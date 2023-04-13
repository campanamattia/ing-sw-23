package Server.Controller;

import Server.Exception.*;
import Interface.CMD;
import Server.Model.*;

import java.util.*;

public class PlayerAction implements CMD {
    private GameModel game;

    public PlayerAction(GameModel game) {
        this.game = game;
    }

    @Override
    public List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException {
        return game.selectedTiles(coordinates);
    }

    @Override
    public void insertTiles(List<Integer> sort, List<Tile> tiles, int column) throws PlayerException{
        game.insertTiles(sort, tiles, column);
    }

    @Override
    public void writeChat(String message) {
        game.writeChat(message);
    }
}
