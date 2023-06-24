package Client.View.Gui;

import Client.ClientApp;
import Client.View.View;
import Enumeration.TurnPhase;
import Utils.Cell;
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
    private final GuiApplication guiApplication;

    public Gui() throws RemoteException {
        super();
        mockModel = new MockModel();
        this.guiApplication = new GuiApplication();
    }

    @Override
    public void updateBoard(MockBoard mockBoard) {
        this.mockModel.setMockBoard(mockBoard);
        guiApplication.updateMockModel(this.mockModel);
        Cell[][] board = mockModel.getMockBoard().getBoard();
        guiApplication.updateBoard(board);
    }

    @Override
    public void updateCommonGoal(MockCommonGoal mockCommonGoal) {
        this.mockModel.update(mockCommonGoal);
        guiApplication.updateMockModel(this.mockModel);
    }

    @Override
    public void updatePlayer(MockPlayer mockPlayer) {
        this.mockModel.update(mockPlayer);
        guiApplication.updateMockModel(this.mockModel);
    }

    @Override
    public void updateChat(ChatMessage message) {
        if (message.to() == null || message.to().equals(localPlayer)) {
            this.mockModel.addMessage(message);
            guiApplication.updateMockModel(this.mockModel);
            guiApplication.newMessageChat(message);
        }
    }

    @Override
    public void showBoard() {
        Cell[][] board = mockModel.getMockBoard().getBoard();
        guiApplication.showBoard(board);
    }

    @Override
    public void showChat() {

    }

    @Override
    public void showStatus() {

    }

    @Override
    public void showShelves() {
        guiApplication.showShelves();
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
    public void newTurn(String playerID) throws RemoteException {
        mockModel.setCurrentPlayer(playerID);
        mockModel.setTurnPhase(TurnPhase.PICKING);
        showAll();
    }

    private void showAll() {
        showBoard();
        showShelves();
        // showStatus();
    }

    @Override
    public void askLobbySize() throws RemoteException {
        guiApplication.askLobbySize();
    }

    @Override
    public void outcomeSelectTiles(List<Tile> selectedTiles) throws RemoteException {
        this.mockModel.setTurnPhase(TurnPhase.INSERTING);
        guiApplication.updateMockModel(this.mockModel);
        guiApplication.outcomeSelectTiles();
    }

    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {
        if (success)
            this.mockModel.setTurnPhase(TurnPhase.PICKING);
    }

    @Override
    public void outcomeException(Exception e) throws RemoteException {
        guiApplication.outcomeException(e);
    }

    @Override
    public void outcomeLogin(String localPlayer, String lobbyID) throws RemoteException {
        ClientApp.localPlayer = localPlayer;
        ClientApp.lobbyID = lobbyID;
        network.startPing(localPlayer, lobbyID);
        guiApplication.outcomeLogin();
        //LoginScene.toLobbySize();
    }

    @Override
    public void askPlayerInfo(List<Map<String, String>> lobbyInfo) throws RemoteException {
        if (lobbyInfo != null) {
            List<String> lobbies = new ArrayList<>(lobbyInfo.get(0).keySet());
            guiApplication.setLobbies(lobbies);
            guiApplication.askPlayerInfo();
        } else {
            guiApplication.setLobbies(null);
            guiApplication.askPlayerInfo();
        }
    }

    @Override
    public void allGame(MockModel mockModel) throws RemoteException {
        this.mockModel = mockModel;
        guiApplication.updateMockModel(this.mockModel);
        if (mockModel.getChat() != null) fixChat();
        newTurn(mockModel.getCurrentPlayer());
    }
    private void fixChat() {
        mockModel.getChat().removeIf(message -> message.to() != null && !message.to().equals(localPlayer));
    }

    @Override
    public void endGame(List<Rank> leaderboard) throws RemoteException {
        guiApplication.endGame(leaderboard);
    }

    @Override
    public void crashedPlayer(String crashedPlayer) throws RemoteException {
        this.mockModel.getPlayer(crashedPlayer).setOnline(false);
        guiApplication.updateMockModel(this.mockModel);
    }

    @Override
    public void reloadPlayer(String reloadPlayer) throws RemoteException {
        this.mockModel.getPlayer(reloadPlayer).setOnline(true);
        guiApplication.updateMockModel(this.mockModel);
    }
}
