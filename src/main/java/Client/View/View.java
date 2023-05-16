package Client.View;

import Client.Network.Network;
import Interface.Client.RemoteView;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;
import Utils.Rank;
import Utils.Tile;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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


    public void updateBoard(MockBoard mockBoard) throws RemoteException {
        mockModel.setMockBoard(mockBoard);
    }


    public void updateCommonGoal(List<MockCommonGoal> mockCommonGoals) throws RemoteException {
        mockModel.setMockCommonGoal(mockCommonGoals);
    }


    public void updatePlayer(MockPlayer mockPlayer) throws RemoteException {
        for (int i=0; i<mockModel.getMockPlayers().size(); i++) {
            if (mockModel.getMockPlayers().get(i).getPlayerID().equals(mockPlayer.getPlayerID())) {
                mockModel.getMockPlayers().set(i,mockPlayer);
            }
        }
    }


    public void updateChat(Stack<ChatMessage> chat) throws RemoteException {
        mockModel.setChat(chat);
    }


    public abstract void showBoard();


    public abstract void showChat();


    public abstract void showStatus();

    public void showShelves () {

    }

    public void showHelp() {

    }

    public void showRank(List<Rank> classification) {

    }






}
