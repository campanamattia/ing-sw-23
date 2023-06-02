package Server.Controller.Phase;

import Server.Model.Player.Player;
import Server.Network.Client.ClientHandler;
import Utils.Rank;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static Server.ServerApp.executorService;
import static Server.ServerApp.logger;

public class EndedMatch{

    public static void doRank(Collection<ClientHandler> values, List<Player> players){
        List<Rank> leaderboard = new ArrayList<>();
        for(Player p : players)
            p.endGame();
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        for(Player p : players)
            leaderboard.add(new Rank(p.getPlayerID(), p.getScore()));
        for(ClientHandler c : values)
            executorService.execute(()-> {
                try {
                    c.remoteView().endGame(leaderboard);
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
    }
}
