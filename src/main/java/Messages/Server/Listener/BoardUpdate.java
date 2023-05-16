package Messages.Server.Listener;

import Client.View.View;
import Interface.Client.RemoteView;
import Messages.ServerMessage;
import Utils.MockObjects.MockBoard;

public class BoardUpdate extends ServerMessage {

    private MockBoard mockBoard;

    public BoardUpdate(MockBoard mockBoard) {
        this.mockBoard = mockBoard;
    }

    public void execute(View view){
        view.updateBoard(this.mockBoard);
    }
}
