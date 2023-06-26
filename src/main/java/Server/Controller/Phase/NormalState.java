package Server.Controller.Phase;

import Exception.GamePhase.EndingStateException;
import Exception.GamePhaseException;
import Server.Model.Player.Player;

import java.util.List;

/**
 * The NormalState class represents the phase where the match is in the normal state.
 */
public class NormalState extends PhaseController {
    /**
     * Create a new NormalState instance.
     * @param currentPlayer The current player.
     * @param players The list of players.
     */
    public NormalState(Player currentPlayer, List<Player> players) {
        super(currentPlayer, players);
    }

    /**
     * Try to find the next player.
     * It checks if the current player has completed the shelf.
     * And if it is, it throws an EndingStateException.
     * @throws GamePhaseException If the next player is the first player.
     */
    @Override
    public void nextPlayer() throws GamePhaseException {
        if(this.currentPlayer.getMyShelf().full())
            throw new EndingStateException();
        do{
            int nextIndex = (this.players.indexOf(this.currentPlayer)+1) % players.size();
            this.currentPlayer = this.players.get(nextIndex);
        }while(! this.currentPlayer.getStatus());
    }
}
