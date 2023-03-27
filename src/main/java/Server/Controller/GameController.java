package Server.Controller;

import Server.Exception.*;
import Server.Model.CMD;
import Server.Model.Coordinates;
import Server.Model.GameModel;
import Server.Model.Tile;

import java.util.List;
import java.util.UUID;

public class GameController implements CMD {
    private GameModel game;
    private UUID uuid = UUID.randomUUID();
    private List<String> playersID;
    private String currPlayer;
    private GameListener gameListener;

    @Override
    public List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException {
        return game.selectedTiles(coordinates);
    }

    @Override
    public void insertTiles(String player, List<Integer> sort, List<Tile> tiles, int column) throws PlayerException {
        game.insertTiles(player, sort, tiles, column);
    }


    @Override
    public void writeChat(String message) {
        game.writeChat(message);
    }
}
