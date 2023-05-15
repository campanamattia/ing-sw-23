package Client.View;

import Interface.Client.RemoteView;
import Utils.MockObjects.MockModel;
import Utils.Rank;
import Utils.Tile;

import java.util.List;
import java.util.Scanner;

public abstract class View implements RemoteView {
    MockModel mockmodel;

    public void askServerInfo() {

    }
    public void askConnectionType() {

    }

    public void showTitle() {

    }
    public void showBoard() {

    }

    public void showTile(List<Tile> tileList) {

    }
    public void showChat() {

    }
    public void showWinner() {

    }
    public void showCommonGoal() {

    }
    public void showShelves () {

    }

    public void showHelp() {

    }

    public void showMessage(String message) {

    }

    public void showRank(List<Rank> classification) {

    }






}
