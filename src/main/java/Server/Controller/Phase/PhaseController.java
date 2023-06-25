package Server.Controller.Phase;

import Exception.GamePhaseException;
import Server.Model.Player.Player;
import java.util.List;

/**
 * The PhaseController class represents the phase where the match is in.
 */
public abstract class PhaseController {
    /**
     * The current player.
     */
    protected Player currentPlayer;
    /**
     * The list of players.
     */
    protected List<Player> players;

    /**
     * Create a new PhaseController instance.
     * @param currentPlayer The current player.
     * @param players The list of players.
     */
    public PhaseController(Player currentPlayer, List<Player> players) {
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    /**
     * Get the current player.
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get the list of players.
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Try to find the next player.
     */
    public abstract void nextPlayer() throws GamePhaseException;
}
