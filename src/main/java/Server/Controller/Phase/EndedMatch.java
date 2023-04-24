package Server.Controller.Phase;

import Server.Model.Player;
import Utils.Rank;

import java.util.List;

public class EndedMatch{
    private List<Rank> leaderboard = null;

    public List<Rank> doRank(List<Player> players){
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        for(Player p : players)
            this.leaderboard.add(new Rank(p.getID(), p.getScore()));
        return this.leaderboard;
    }
}
