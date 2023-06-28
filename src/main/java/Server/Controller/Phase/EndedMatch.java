package Server.Controller.Phase;

import Server.Model.Player.Player;
import Utils.Rank;

import java.util.ArrayList;
import java.util.List;


/**
 * The EndedMatch class represents the phase where the match has ended.
 */
public class EndedMatch{
    /**
     * Ends the match and sends the leaderboard to all the players.
     * @param players The list of players.
     */
    public static List<Rank> doRank(List<Player> players){
        for(Player player : players)
            player.endGame();

        List<Rank> leaderboard = new ArrayList<>();

        for (Player player : players){
            if (leaderboard.isEmpty()){
                leaderboard.add(new Rank(player.getPlayerID(), player.getScore()));
                continue;
            }
            for (Rank rank : leaderboard){
                if (player.getScore() > rank.score()){
                    leaderboard.add(leaderboard.indexOf(rank), new Rank(player.getPlayerID(), player.getScore()));
                    break;
                }
            }
        }
        return leaderboard;
    }
}
