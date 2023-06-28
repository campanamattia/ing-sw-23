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

public abstract class View extends UnicastRemoteObject implements RemoteView {
    protected MockModel mockModel;

    @SuppressWarnings("BlockingMethodInNonBlockingContext")
    public View() throws RemoteException {
        super();
    }
    public abstract void updateBoard(MockBoard mockBoard);

    public abstract void updateCommonGoal(MockCommonGoal mockCommonGoal);

    public abstract void updatePlayer(MockPlayer mockPlayer);

    public abstract void updateChat(ChatMessage message);

    public MockModel getMockModel(){
        return mockModel;
    }
}