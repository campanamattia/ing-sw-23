package Client.View.Gui;

import Client.ClientApp;
import Client.View.View;
import Enumeration.GameWarning;
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
import java.util.List;
import java.util.Map;

import static Client.ClientApp.*;

/**
 * This class represents a graphic interface for the application.
 * It extends the View class and provides methods for interacting with the user scenes and active listeners.
 * The GUI communicates with GuiApplication and displays the game board, chat messages, and player updates.
 */
public class Gui extends View {
    private final GuiApplication guiApplication;

    /**
     * Class constructor.
     */
    public Gui() throws RemoteException {
        super();
        mockModel = new MockModel();
        this.guiApplication = new GuiApplication();
    }

    /**
     * Receive the board updated from the server.
     * @param mockBoard MockBoard is a class that contains all the information about the board.
     */
    @Override
    public void updateBoard(MockBoard mockBoard) {
        this.mockModel.setMockBoard(mockBoard);
        guiApplication.updateMockModel(this.mockModel);
        Cell[][] board = mockModel.getMockBoard().getBoard();
        guiApplication.updateBoard(board);
    }

    /**
     * Receive update concerning common goal.
     * @param mockCommonGoal MockCommon goal is a class that contains all the information about common goals.
     */
    @Override
    public void updateCommonGoal(MockCommonGoal mockCommonGoal) {
        this.mockModel.update(mockCommonGoal);
        guiApplication.updateMockModel(this.mockModel);
        guiApplication.updateCommonGoal(mockCommonGoal);
    }

    /**
     * Receive update of the player.
     * @param mockPlayer MockPlayer is a class that contains all the information about the player.
     */
    @Override
    public void updatePlayer(MockPlayer mockPlayer) {
        this.mockModel.update(mockPlayer);
        guiApplication.updateMockModel(this.mockModel);
    }

    /**
     * Receive from the server the message sent by a player which have to be printed in the chat.
     * @param message It's the message.
     */
    @Override
    public void updateChat(ChatMessage message) {
        if (message.to() == null || message.to().equals(localPlayer) || message.from().equals(localPlayer)) {
            this.mockModel.addMessage(message);
            guiApplication.updateMockModel(this.mockModel);
            guiApplication.newMessageChat(message);
        }
    }

    /**
     * The server call this method when the previous turn in ended and a new one must start.
     * @param playerID It's the player that has to play.
     * ShowALl() it's a private method which show all the boards.
     */
    @Override
    public void newTurn(String playerID) throws RemoteException {
        mockModel.setCurrentPlayer(playerID);
        mockModel.setTurnPhase(TurnPhase.PICKING);
        showAll();
        guiApplication.writeCurrentPlayer(playerID);
        guiApplication.setUp();
    }

    private void showAll() {
        guiApplication.showBoard(mockModel.getMockBoard().getBoard());
        guiApplication.showShelves();
        // showStatus();
    }

    /**
     * If you are the first player, the server will call you this method, and you will be redirected to AskLobbySizeScene
     */
    @Override
    public void askLobbySize() throws RemoteException {
        guiApplication.askLobbySize();
    }

    /**
     * After a player has selected his tiles, if the move is valid, the server will send back the tiles for the
     * inserting.
     * @param selectedTiles tiles selected from the player.
     */
    @Override
    public void outcomeSelectTiles(List<Tile> selectedTiles) throws RemoteException {
        this.mockModel.setTurnPhase(TurnPhase.INSERTING);
        guiApplication.updateHelp();
        guiApplication.updateMockModel(this.mockModel);
        guiApplication.outcomeSelectTiles();
    }

    /**
     * It's the result of the inserting phase.
     * @param success it explains itself.
     */
    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {
        if (success) {
            guiApplication.outcomeInsertTiles();
            this.mockModel.setTurnPhase(TurnPhase.PICKING);
        }
    }

    /**
     * The server will call this method to show any type of exceptions.
     * @param e exception.
     */
    @Override
    public void outcomeException(Exception e) throws RemoteException {
        guiApplication.outcomeException(e);
    }

    /**
     * It's the result of the login phase.
     * @param localPlayer Connecting the username chosen in the login phase and the local player.
     * @param lobbyID Lobby in which the player wants to join.
     */
    @Override
    public void outcomeLogin(String localPlayer, String lobbyID) throws RemoteException {
        ClientApp.localPlayer = localPlayer;
        ClientApp.lobbyID = lobbyID;
        network.startPing(localPlayer, lobbyID);
        guiApplication.outcomeLogin();
        //LoginScene.toLobbySize();
    }

    /**
     * During the Login phase, will be asked all the information about the player like playerID, lobby to join.
     * @param lobbyInfo Contains the lobbies and games already open.
     */
    @Override
    public void askPlayerInfo(List<Map<String, String>> lobbyInfo) throws RemoteException {
        guiApplication.askPlayerInfo(lobbyInfo);
    }

    /**
     * Receiving from the server a light model, that contains all the information about the current game.
     * @param mockModel Model of an MVC pattern but simplified.
     */
    @Override
    public void allGame(MockModel mockModel) throws RemoteException {
        this.mockModel = mockModel;
        if(!mockModel.getChat().isEmpty()) {
            fixChat();
            guiApplication.refreshChat(mockModel.getChat());
        }
        guiApplication.updateMockModel(this.mockModel);
        if (mockModel.getChat() != null) fixChat();
        newTurn(mockModel.getCurrentPlayer());
    }
    private void fixChat() {
        mockModel.getChat().removeIf(message -> message.to() != null && !message.to().equals(localPlayer) && !message.from().equals(localPlayer));
    }

    /**
     * Receiving the final rank to print the results of the game.
     * @param leaderboard scoring of every player.
     */
    @Override
    public void endGame(List<Rank> leaderboard) throws RemoteException {
        System.out.println(leaderboard);
        guiApplication.endGame(leaderboard);
        guiApplication.updateRanks(leaderboard);
    }

    /**
     * Notification if a player has crashed.
     * @param crashedPlayer playerID of the crashed player.
     */
    @Override
    public void crashedPlayer(String crashedPlayer) throws RemoteException {
        this.mockModel.getPlayer(crashedPlayer).setOnline(false);
        guiApplication.updateMockModel(this.mockModel);
    }

    /**
     * Method called if a crashed player tries to rejoin the match.
     * @param reloadPlayer playerID of the player who wants to rejoin.
     */
    @Override
    public void reloadPlayer(String reloadPlayer) throws RemoteException {
        this.mockModel.getPlayer(reloadPlayer).setOnline(true);
        guiApplication.updateMockModel(this.mockModel);
    }

    /**
     * Message to be print in case of a warning, like a player crashed.
     * @param warning warning to be printed.
     */
    @SuppressWarnings("BlockingMethodInNonBlockingContext")
    @Override
    public void outcomeMessage(GameWarning warning) throws RemoteException {
        guiApplication.outcomeMessage(warning.getMs().toUpperCase());

        switch(warning){
            case WON -> executorService.execute(() -> {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //System.exit(0);
                System.out.println("Game ended");
            });
            case LAST_ROUND -> guiApplication.lastRound();
        }
    }
}
