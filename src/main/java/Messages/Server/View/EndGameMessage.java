package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;
import Utils.Rank;

import java.rmi.RemoteException;
import java.util.List;

public class EndGameMessage extends ServerMessage {
    private final List<Rank> rank;

    public EndGameMessage(List<Rank> rank){
        this.rank = rank;
    }

    @Override
    public void execute(View view) {
        try {
            view.endGame(this.rank);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
