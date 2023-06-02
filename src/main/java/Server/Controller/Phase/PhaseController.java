package Server.Controller.Phase;

import Exception.CommonGoal.NullPlayerException;
import Exception.GamePhaseException;
import Server.Model.LivingRoom.CommonGoal.CommonGoal;
import Server.Model.Player.Player;
import Server.ServerApp;
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

    public void checkCommonGoals(List<CommonGoal> commonGoals){
        for(CommonGoal common : commonGoals)
            if(!common.getAccomplished().contains(this.currentPlayer.getPlayerID()))
                try{
                    common.check(this.currentPlayer);
                }catch(NullPlayerException e){
                    ServerApp.logger.severe(e.getMessage());
                }
    }
}
