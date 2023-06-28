package Server.Controller.Phase;

import Exception.GamePhase.EndGameException;
import Exception.GamePhaseException;
import Server.Model.Player.Player;

import java.util.List;

/**
 * The LastRoundState class represents the phase where the match is in the last round.
 */
public class LastRoundState extends PhaseController {
    private String firstPlayer;
    /**
     * Create a new LastRoundState instance.
     * @param currentPlayer The current player.
     * @param players The list of players.
     */
    public LastRoundState(Player currentPlayer, List<Player> players) {
        super(currentPlayer, players);
    }

    /**
     * Try to find the next player.
     * It checks if the next player is the first player. And if it is, it throws an EndGameException.
     * @throws GamePhaseException If the next player is the first player.
     */
    @Override
    public void nextPlayer() throws GamePhaseException {
        do{
            int nextIndex = (this.players.indexOf(this.currentPlayer)+1) % players.size();
            if(nextIndex == 0 || players.get(nextIndex).getPlayerID().equals(this.firstPlayer))
                throw new EndGameException();
            this.currentPlayer = this.players.get(nextIndex);
        }while(! this.currentPlayer.getStatus());
    }

    /**
     * Set the first player.
     * @param firstPlayer The first player.
     */
    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
}
