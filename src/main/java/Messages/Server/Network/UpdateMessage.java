package Messages.Server.Network;

import Client.View.View;
import Messages.ServerMessage;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;

public class UpdateMessage extends ServerMessage{
    private final MockBoard board;
    private final MockCommonGoal commonGoal;
    private final MockPlayer player;
    private final ChatMessage message;

    public UpdateMessage(MockBoard board) {
        this.board = board;
        this.commonGoal = null;
        this.player = null;
        this.message = null;
    }

    public UpdateMessage(MockPlayer player) {
        this.board = null;
        this.commonGoal = null;
        this.player = player;
        this.message = null;
    }

    public UpdateMessage(MockCommonGoal commonGoal) {
        this.board = null;
        this.commonGoal = commonGoal;
        this.player = null;
        this.message = null;
    }

    public UpdateMessage(ChatMessage message) {
        this.board = null;
        this.commonGoal = null;
        this.player = null;
        this.message = message;
    }

    @Override
    public void execute(View view) {
        if(this.board != null) {
            view.updateBoard(this.board);
        }
        if(this.commonGoal != null) {
            view.updateCommonGoal(this.commonGoal);
        }
        if(this.player != null) {
            view.updatePlayer(this.player);
        }
        if(this.message != null) {
            view.updateChat(this.message);
        }
    }
}
