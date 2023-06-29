package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;
import Utils.Rank;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents a server message indicating the end of the game and providing the final ranking of players.
 * This message is used to notify the client's view about the end of the game and the ranking results.
 */
public class EndGameMessage extends ServerMessage {
    private final List<Rank> rank;

    /**
     * Constructs a new EndGameMessage with the specified ranking.
     *
     * @param rank the list of Rank objects representing the final ranking of players.
     */
    public EndGameMessage(List<Rank> rank){
        this.rank = rank;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view
     * to handle the end of the game event and provide the final ranking.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.endGame(this.rank);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
