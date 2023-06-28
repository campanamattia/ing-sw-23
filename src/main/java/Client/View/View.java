package Client.View;

import Client.View.Gui.GuiApplication;
import Interface.Client.RemoteView;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Abstract class representing a view in the game.
 */
public abstract class View extends UnicastRemoteObject implements RemoteView {
    protected MockModel mockModel;

    /**
     * Constructs a new View object.
     *
     * @throws RemoteException If an exception occurs during remote communication.
     */
    public View() throws RemoteException {
        super();
    }

    /**
     * Updates the board in the view with the provided mock board.
     *
     * @param mockBoard The mock board to update.
     */
    public abstract void updateBoard(MockBoard mockBoard);

    /**
     * Updates the common goal in the view with the provided mock common goal.
     *
     * @param mockCommonGoal The mock common goal to update.
     */
    public abstract void updateCommonGoal(MockCommonGoal mockCommonGoal);

    /**
     * Updates the player in the view with the provided mock player.
     *
     * @param mockPlayer The mock player to update.
     */
    public abstract void updatePlayer(MockPlayer mockPlayer);

    /**
     * Updates the chat with the provided chat message.
     *
     * @param message The chat message to update.
     */
    public abstract void updateChat(ChatMessage message);

    /**
     * Retrieves the mock model associated with the view.
     *
     * @return The mock model.
     */
    public MockModel getMockModel(){
        return mockModel;
    }
}