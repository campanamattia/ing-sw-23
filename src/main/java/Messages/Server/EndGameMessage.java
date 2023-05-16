package Messages.Server;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;
import Utils.Rank;

import java.util.List;

public class EndGameMessage extends ServerMessage {
    private List<Rank> rank;

    public EndGameMessage(){
        super();
        this.rank = null;
    }

    public EndGameMessage(List<Rank> rank){
        this.messageType = MessageType.ENDGAME;
        this.rank = rank;
    }

    public List<Rank> getRank() {
        return this.rank;
    }
    public void setRank(List<Rank> rank) {
        this.rank = rank;
    }

    @Override
    public void execute(View view) {
        view.showRank(this.rank);
    }
}
