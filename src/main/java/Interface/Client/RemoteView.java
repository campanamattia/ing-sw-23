package Interface.Client;

import Utils.MockObjects.MockModel;
import Utils.Rank;
import Utils.Tile;

import java.util.Collection;
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteView extends Remote {

    void updateLobby(String loggedPlayer) throws RemoteException;

    void newTurn(String currentPlayer) throws RemoteException;

    void askLobbySize() throws RemoteException;

    void outcomeSelectTiles(List<Tile> selectedTiles) throws RemoteException;

    void outcomeInsertTiles(boolean success) throws RemoteException;

    void outcomeException(Exception e) throws RemoteException;

    void outcomeLogin(String localPlayer) throws RemoteException;

    void askPlayerInfo(List<Collection<String>> lobbyInfo) throws RemoteException;

    void allGame(MockModel mockModel) throws RemoteException;

    void endGame(List<Rank> leaderboard) throws RemoteException;

    void crashedPlayer(String crashedPlayer) throws RemoteException;

    void reloadPlayer(String reloadPlayer) throws RemoteException;
}
