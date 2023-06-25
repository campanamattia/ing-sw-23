package Client.View.Gui;

import Client.View.View;
import Utils.MockObjects.MockModel;
import Utils.Rank;
import Utils.Tile;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public abstract class Gui extends View {

    public Gui() throws RemoteException {
    }

    public void showBoard() {

    }

    public void showChat() {

    }

    public void showStatus() {

    }


    @Override
    public void newTurn(String currentPlayer) throws RemoteException {

    }

    @Override
    public void askLobbySize() throws RemoteException {

    }

    @Override
    public void outcomeSelectTiles(List<Tile> selectedTiles) throws RemoteException {

    }

    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeException(Exception e) throws RemoteException {

    }

    @Override
    public void outcomeLogin(String localPlayer, String lobbyID) throws RemoteException {

    }

    @Override
    public void askPlayerInfo(List<Map<String, String>> lobbyInfo) throws RemoteException {

    }

    @Override
    public void allGame(MockModel mockModel) throws RemoteException {

    }

    @Override
    public void endGame(List<Rank> leaderboard) throws RemoteException {

    }

    @Override
    public void crashedPlayer(String crashedPlayer) throws RemoteException {

    }

    @Override
    public void reloadPlayer(String reloadPlayer) throws RemoteException {

    }
}


