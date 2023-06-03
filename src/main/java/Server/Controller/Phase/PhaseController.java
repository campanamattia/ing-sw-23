package Server.Controller.Phase;

import Exception.GamePhaseException;
import Server.Model.Player.Player;
import java.util.List;

public abstract class PhaseController {
    protected Player currentPlayer;
    protected List<Player> players;

    public PhaseController(Player currentPlayer, List<Player> players) {
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public abstract void nextPlayer() throws GamePhaseException;
}
