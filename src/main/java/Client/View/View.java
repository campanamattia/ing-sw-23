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

    public void updateBoard(MockBoard mockBoard) {
        mockModel.setMockBoard(mockBoard);
    }

    public void updateCommonGoal(MockCommonGoal mockCommonGoals) {
        mockModel.setMockCommonGoal(mockCommonGoals);
    }

    public void updatePlayer(MockPlayer mockPlayer) {
        for (int i = 0; i < mockModel.getMockPlayers().size(); i++) {
            if (mockModel.getMockPlayers().get(i).getPlayerID().equals(mockPlayer.getPlayerID())) {
                mockModel.getMockPlayers().set(i, mockPlayer);
            }
        }
    }

    public void updateChat(ChatMessage chat) {
        mockModel.addMessage(chat);
    }

    public abstract void showBoard();

    public abstract void showChat();

    public abstract void showStatus();

    public abstract void showShelves();

    public abstract void showHelp();

    public abstract void showGame();

    public abstract void showRank(List<Rank> classification);
}