package Server.Controller.Phase;

import Enumeration.*;
import Exception.GamePhase.EndingStateException;
import Exception.GamePhaseException;
import Server.Controller.PhaseController;
import Server.Model.Player;

import java.util.List;

public class NormalState extends PhaseController {
    public NormalState(Player currentPlayer, List<Player> players) {
        super(currentPlayer, players);
        this.phase = GamePhase.ONGOING;
    }

    @Override
    public void nextPlayer() throws GamePhaseException {
        if(this.currentPlayer.getMyShelf().full())
            throw new EndingStateException();
        do{
            int nextIndex = (this.players.indexOf(this.currentPlayer)+1) % players.size();
            this.currentPlayer = this.players.get(nextIndex);
        }while(this.currentPlayer.getStatus());
    }

}
