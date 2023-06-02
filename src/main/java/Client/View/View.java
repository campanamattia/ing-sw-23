package Client.View;

import Client.Network.Network;
import Interface.Client.RemoteView;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;
import Utils.Rank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public abstract class View extends UnicastRemoteObject implements RemoteView {
    protected MockModel mockModel;
    protected Network network;

    public View() throws RemoteException {
        super();
    }

    public Network getNetwork() {
        if (network == null) {
            throw new RuntimeException("Network not set");
        } else return this.network;
    }

    public void setNetwork(Network network) {
        this.network = network;
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

    public abstract void showGame();

    public abstract void showRank(List<Rank> classification);
}