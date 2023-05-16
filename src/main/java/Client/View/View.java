package Client.View;

import Client.Network.Network;
import Interface.Client.RemoteView;
import Utils.MockObjects.MockModel;
import Utils.Rank;
import Utils.Tile;

import java.util.List;
import java.util.Scanner;

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

    public void showBoard() {

    }

    public void showTile(List<Tile> tileList) {

    }
    public void showChat() {

    }


    public void showShelves () {

    }

    public void showHelp() {

    }

    public void showRank(List<Rank> classification) {

    }






}
