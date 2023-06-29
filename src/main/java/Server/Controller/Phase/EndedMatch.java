package Server.Controller.Phase;

import Server.Model.Player.Player;
import Utils.Rank;

import java.util.ArrayList;
import java.util.List;

import static Server.ServerApp.logger;


/**
 * The EndedMatch class represents the phase where the match has ended.
 */
public class EndedMatch {
    /**
     * Ends the match and sends the leaderboard to all the players.
     *
     * @param players The list of players.
     */
    public static List<Rank> doRank(List<Player> players) {
        for (Player player : players)
            player.endGame();

        List<Rank> list = new ArrayList<>();
        for (Player player : players)
            list.add(new Rank(player.getPlayerID(), player.getPersonalScore(), player.getPatternScore(), player.getSharedScore(), player.getTotalScore()));

        List<Rank> leaderboard = new ArrayList<>();
        for (Rank rank : list){
            if (leaderboard.isEmpty()){
                leaderboard.add(rank.clone());
                continue;
            }
            for (int i = 0; i < leaderboard.size(); i++){
                if (rank.getTotalScore() > leaderboard.get(i).getTotalScore()){
                    leaderboard.add(i, rank.clone());
                    break;
                }
                if (i == leaderboard.size() - 1){
                    leaderboard.add(rank.clone());
                    break;
                }
            }
        }
        logger.info("Leaderboard: " + leaderboard);
        return leaderboard;
    }
}
