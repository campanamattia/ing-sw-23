package Server.Controller.Players;

import Server.Model.Player.Player;
import Utils.Tile;

import java.util.List;

public class CurrentPlayer {
    private Player currentPlayer;
    private List<Tile> tiles;

    public CurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.tiles = null;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void reset(Player player){
        this.currentPlayer = player;
        this.tiles = null;
    }
}
