package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;
import Utils.MockObjects.MockModel;

import java.rmi.RemoteException;

/**
 * Represents a server message containing the full game state to be sent to the client's view.
 * This message is used to update the client's view with the complete game state.
 */
public class AllGameMessage extends ServerMessage {
    private final MockModel mockModel;

    /**
     * Constructs an AllGameMessage object with the specified MockModel.
     *
     * @param mockModel the MockModel representing the complete game state.
     */
    public AllGameMessage(MockModel mockModel) {
        this.mockModel = mockModel;
    }

    /**
     * Executes the server message by invoking the corresponding method in the client's view to update the game state.
     *
     * @param view the View object representing the client's view.
     * @throws RuntimeException if a RemoteException occurs during the execution.
     */
    @Override
    public void execute(View view) {
        try {
            view.allGame(this.mockModel);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
