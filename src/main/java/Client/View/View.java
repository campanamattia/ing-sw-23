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

    public View() throws RemoteException {
        super();
    }

    public abstract void updateBoard(MockBoard mockBoard);

    public abstract void updateCommonGoal(MockCommonGoal mockCommonGoal);

    public abstract void updatePlayer(MockPlayer mockPlayer);

    public abstract void updateChat(ChatMessage message);

    public abstract void showBoard();

    public abstract void showChat();

    public abstract void showStatus();

    public abstract void showShelves();

    public abstract void showHelp();

    public abstract void start();

    public abstract void printError(String error);

    public abstract void printMessage(String message);

    public MockModel getMockModel(){
        return mockModel;
    }

    public abstract void updateGuiApplication(GuiApplication guiApplication);
}