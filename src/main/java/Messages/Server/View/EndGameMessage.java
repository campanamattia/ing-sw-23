package Messages.Server.View;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;
import Utils.Rank;

import java.util.List;

public class EndGameMessage extends ServerMessage {
    private final List<Rank> rank;

    public EndGameMessage(List<Rank> rank){
        this.rank = rank;
    }

    @Override
    public void execute(View view) {
        view.showRank(this.rank);
    }
}
