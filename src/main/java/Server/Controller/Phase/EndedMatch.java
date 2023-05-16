package Server.Controller.Phase;

import Server.Model.Player.Player;
import Utils.Rank;

import java.util.ArrayList;
import java.util.List;

public class EndedMatch{

    public static List<Rank> doRank(List<Player> players){
        List<Rank> leaderboard = new ArrayList<>();
        for(Player p : players)
            p.endGame();
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        for(Player p : players)
            leaderboard.add(new Rank(p.getPlayerID(), p.getScore()));
        return leaderboard;
    }
}
