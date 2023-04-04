package Server.Controller;
import Enumeration.GamePhase;
import Exception.GamePhaseException;
import Server.Model.CommonGoal;
import Server.Model.Player;

import java.util.List;

public abstract class PhaseController {
    protected GamePhase phase;
    protected Player currentPlayer;
    protected List<Player> players;

    public PhaseController(Player currentPlayer, List<Player> players) {
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public abstract void nextPlayer() throws GamePhaseException;

    public void endTurn(List<CommonGoal> commonGoals){
        for(CommonGoal common : commonGoals)
            if(!common.getAccomplished().contains(this.currentPlayer.getID()))
                common.check(this.currentPlayer);
    }
}
