package Server.Controller.Phase;

import Server.Model.Player.Player;
import Utils.Rank;

import java.util.List;

public class EndedMatch{
    private List<Rank> leaderboard = null;

    public List<Rank> doRank(List<Player> players){
        for(Player p : players)
            p.endGame();
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        for(Player p : players)
            this.leaderboard.add(new Rank(p.getPlayerID(), p.getScore()));
        return this.leaderboard;
    }
}
