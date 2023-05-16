package Messages.Server.Listener;

import Client.View.View;
import Messages.ServerMessage;
import Utils.MockObjects.MockBoard;

public class BoardUpdate extends ServerMessage {

    private final MockBoard mockBoard;

    public BoardUpdate(MockBoard mockBoard) {
        this.mockBoard = mockBoard;
    }

    public void execute(View view){
        view.updateBoard(this.mockBoard);
    }
}
