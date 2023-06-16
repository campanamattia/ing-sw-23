package Client.View.Gui;

import Client.ClientApp;
import Client.View.View;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;
import Utils.Rank;
import Utils.Tile;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Client.ClientApp.*;

public class Gui extends View {
    private GuiApplication guiApplication;

    public Gui() throws RemoteException {
        super();
        mockModel = new MockModel();
    }

    public void updateGuiApplication(GuiApplication guiApplication){
        this.guiApplication = guiApplication;
        guiApplication.updateGui(this);
    }

    @Override
    public void updateBoard(MockBoard mockBoard) {

    }

    @Override
    public void updateCommonGoal(MockCommonGoal mockCommonGoal) {

    }

    @Override
    public void updatePlayer(MockPlayer mockPlayer) {

    }

    @Override
    public void updateChat(ChatMessage message) {

    }

    @Override
    public void showBoard() {

    }

    @Override
    public void showChat() {

    }

    @Override
    public void showStatus() {

    }

    @Override
    public void showShelves() {

    }

    @Override
    public void showHelp() {

    }

    @Override
    public void start() {

    }

    @Override
    public void printError(String error) {

    }

    @Override
    public void printMessage(String message) {

    }


    @Override
    public void newTurn(String currentPlayer) throws RemoteException {

    }

    @Override
    public void askLobbySize() throws RemoteException {
        guiApplication.setFirstPlayer(true);
        Integer playerNumber = guiApplication.getLobbySize();
        network.setLobbySize(localPlayer,lobbyID,playerNumber);
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
        ClientApp.localPlayer = localPlayer;
        ClientApp.lobbyID = lobbyID;
        guiApplication.outcomeLogin("Logged in!");
        network.startPing(localPlayer, lobbyID);
    }

    @Override
    public void askPlayerInfo(List<Map<String, String>> lobbyInfo) throws RemoteException {
        if(lobbyInfo != null){
            List<String> lobbies = new ArrayList<>(lobbyInfo.get(0).keySet());
            guiApplication.setLobbies(lobbies);
        }else{
            guiApplication.setLobbies(null);
        }
        List<String> playerInfo;
        playerInfo = guiApplication.getPlayerInfo();
        while(playerInfo==null)
            playerInfo = guiApplication.getPlayerInfo();

        network.login(playerInfo.get(0),playerInfo.get(1),this,network);
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


