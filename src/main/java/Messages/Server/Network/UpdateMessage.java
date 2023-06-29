package Messages.Server.Network;

import Client.View.View;
import Messages.ServerMessage;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;

/**
 * Represents a server message that updates the client's view with new information.
 * This message can update the board, common goal, player, or chat messages in the client's view.
 */
public class UpdateMessage extends ServerMessage{
    private final MockBoard board;
    private final MockCommonGoal commonGoal;
    private final MockPlayer player;
    private final ChatMessage message;

    /**
     * Constructs an UpdateMessage to update the board in the client's view.
     *
     * @param board the updated board object.
     */
    public UpdateMessage(MockBoard board) {
        this.board = board;
        this.commonGoal = null;
        this.player = null;
        this.message = null;
    }

    /**
     * Constructs an UpdateMessage to update the player in the client's view.
     *
     * @param player the updated player object.
     */
    public UpdateMessage(MockPlayer player) {
        this.board = null;
        this.commonGoal = null;
        this.player = player;
        this.message = null;
    }


    /**
     * Constructs an UpdateMessage to update the common goal in the client's view.
     *
     * @param commonGoal the updated common goal object.
     */
    public UpdateMessage(MockCommonGoal commonGoal) {
        this.board = null;
        this.commonGoal = commonGoal;
        this.player = null;
        this.message = null;
    }

    /**
     * Constructs an UpdateMessage to update the chat messages in the client's view.
     *
     * @param message the new chat message to be added to the view.
     */
    public UpdateMessage(ChatMessage message) {
        this.board = null;
        this.commonGoal = null;
        this.player = null;
        this.message = message;
    }

    /**
     * Executes the server message by invoking appropriate methods in the client's view
     * to update the board, common goal, player, or chat messages based on the message contents.
     *
     * @param view the View object representing the client's view.
     */
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
