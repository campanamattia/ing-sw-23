package Server.Controller.Phase;
import Enumeration.GamePhase;
import Exception.CommonGoal.NullPlayerException;
import Exception.GamePhaseException;
import Server.Model.LivingRoom.CommonGoal.CommonGoal;
import Server.Model.Player.Player;

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

    public void checkCommonGoals(List<CommonGoal> commonGoals){
        for(CommonGoal common : commonGoals)
            if(!common.getAccomplished().contains(this.currentPlayer.getPlayerID()))
                try{
                    common.check(this.currentPlayer);
                }catch(NullPlayerException e){
                    System.out.println(e.toString());
                }
    }
}
