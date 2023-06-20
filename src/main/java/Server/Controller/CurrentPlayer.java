package Server.Controller;

import Server.Model.Player.Player;
import Utils.Tile;

import java.util.List;

/**
 * The CurrentPlayer class represents the current player and the tiles he has selected.
 */
public class CurrentPlayer {
    /**
     * The current player.
     */
    private Player currentPlayer;
    /**
     * The tiles selected by the current player.
     */
    private List<Tile> tiles;

    /**
     * Create a new CurrentPlayer instance with the provided player.
     * @param currentPlayer The current player.
     */
    public CurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.tiles = null;
    }

    /**
     * Get the current player.
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set the current player.
     * @param currentPlayer The current player.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Get the tiles selected by the current player.
     * @return The tiles selected by the current player.
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Set the tiles selected by the current player.
     * @param tiles The tiles selected by the current player.
     */
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    /**
     * Reset the current player and the tiles he has selected.
     * It is used when the current player has finished his turn.
     * @param player The current player.
     */
    public void reset(Player player){
        this.currentPlayer = player;
        this.tiles = null;
    }
}
