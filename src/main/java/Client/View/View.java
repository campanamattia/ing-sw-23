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
import java.util.List;

public abstract class View implements RemoteView {
    MockModel mockModel;
    Network network;

    public MockModel getMockmodel() {
        return mockModel;
    }

    public void setMockModel(MockModel mockmodel) {
        this.mockModel = mockmodel;
    }

    public Network getNetwork() {
        return network;
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