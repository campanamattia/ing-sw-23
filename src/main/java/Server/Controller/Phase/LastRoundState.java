package Server.Controller.Phase;

import Exception.GamePhase.EndGameException;
import Exception.GamePhaseException;
import Server.Model.Player.Player;

import java.util.List;

public class LastRoundState extends PhaseController {
    public LastRoundState(Player currentPlayer, List<Player> players) {
        super(currentPlayer, players);
    }

    @Override
    public void nextPlayer() throws GamePhaseException {
        do{
            int nextIndex = (this.players.indexOf(this.currentPlayer)+1) % players.size();
            if(nextIndex == 0)
                throw new EndGameException();
            this.currentPlayer = this.players.get(nextIndex);
        }while(! this.currentPlayer.getStatus());
    }
}
