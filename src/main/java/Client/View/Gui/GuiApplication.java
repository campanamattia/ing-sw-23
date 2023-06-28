package Client.View.Gui;

import Client.View.Gui.Scene.ConnectionScene;
import Client.View.Gui.Scene.LivingRoom;
import Client.View.Gui.Scene.LoginScene;
import Utils.Cell;
import Utils.ChatMessage;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.Rank;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static Client.ClientApp.localPlayer;


public class GuiApplication extends Application {

    private Stage primaryStage;
    private List<String> activeLobbies = new ArrayList<>();
    private List<String> activeGames = new ArrayList<>();

    private static volatile boolean javaFxLaunched = false;

    /**
     * Class constructor.
     */
    public GuiApplication() {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(() -> Application.launch(GuiApplication.class)).start();
            javaFxLaunched = true;
        }
    }

    /**
     * Initialise the stage to show on screen.
     *
     * @param stage stage to be printed.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        Scene connectionScene = new ConnectionScene(this);
        this.switchScene(connectionScene);
        primaryStage.show();
    }

    /**
     * Method to switch from a scene to another.
     *
     * @param scene scene to show.
     */
    public void switchScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    /**
     * Setter of lobbies.
     *
     * @param lobbies lobby to set.
     */
    public void setLobbies(List<String> lobbies) {
        this.activeLobbies = lobbies;
    }

    /**
     * Setter of active games.
     *
     * @param activeGames active game to set.
     */
    public void setActiveGames(List<String> activeGames) {
        this.activeGames = activeGames;
    }

    /**
     * Call the method toLobbyScene which brings the player to Lobby Scene.
     */
    public void outcomeLogin() {
        Platform.runLater(LoginScene::toLobbyScene);
    }

    /**
     * If you are the first player you'll be redirected from Lobby Scene to the scene in which you'll choose the
     * number of the players that will join the game.
     */
    public void askLobbySize() {
        Platform.runLater(LivingRoom::toLobbySize);
    }

    /**
     * This method will call toLoginScene that will redirect the player to the Login Scene.
     */
    public void askPlayerInfo() {
        Platform.runLater(() -> ConnectionScene.toLoginScene(this.activeLobbies, this.activeGames));
    }

    private boolean firstBoard = true;

    /**
     * This method calls showBoard that will show the board on screen.
     *
     * @param board to be shown.
     */
    public void showBoard(Cell[][] board) {
        if (firstBoard) {
            firstBoard = false;
            Platform.runLater(() -> LivingRoom.showBoard(board));
        }
    }

    private boolean firstShelves = true;

    /**
     * This method call other showCommonAndShelves and updateShelves that will update and show the boards and
     * common goals.
     */
    public void showShelves() {
        if (firstShelves) {
            System.out.println("first ever print shelves!");
            firstShelves = false;
            Platform.runLater(LivingRoom::showCommonAndShelves);
        } else {
            Platform.runLater(LivingRoom::updateShelves);
        }
    }

    /**
     * Calls outcomeSelectTiles that will show on screen the selected tiles.
     */
    public void outcomeSelectTiles() {
        Platform.runLater(LivingRoom::outcomeSelectTiles);
    }

    /**
     * Calls printError that will show the exception.
     * @param e exception to be shown.
     */
    public void outcomeException(Exception e) {
        Platform.runLater(() -> LivingRoom.printError(e.getMessage()));
    }

    /**
     * Calls updateBoard that will show the board with the updates.
     * @param board board updated.
     */
    public void updateBoard(Cell[][] board) {
        Platform.runLater(() -> LivingRoom.updateBoard(board));
    }

    /**
     * Calls updateMockModel that will update the mock model.
     * @param mockModel most recent version of mock model.
     */
    public void updateMockModel(MockModel mockModel) {
        Platform.runLater(() -> LivingRoom.updateMockModel(mockModel));
    }

    /**
     * Calls endGame that will show the final rank.
     * @param leaderboard rank of players.
     */
    public void endGame(List<Rank> leaderboard) {
        Platform.runLater(() -> LivingRoom.endGame(leaderboard));
    }

    /**
     * Calls newMessageChat that will show the new message in chat.
     * @param message message to show.
     */
    public void newMessageChat(ChatMessage message) {
        Platform.runLater(() -> LivingRoom.newMessageChat(message));
    }

    /**
     * Calls writeInfos that will print that it's playerID's turn.
     */
    public void writeCurrentPlayer(String playerID) {
        String personalMessage = "System: Select from 1 to 3 tiles!".toUpperCase();
        if (!localPlayer.equals(playerID)) personalMessage = "";
        String info = (!playerID.equals(localPlayer)) ? ("System: " + playerID + "'s turn!\n" + personalMessage).toUpperCase() : "System: YOUR TURN".toUpperCase();
        Platform.runLater(() -> LivingRoom.writeInfos(info));
    }

    /**
     * Calls writeInfos that will print the information on the chat.
     */
    public void updateHelp() {
        String info = "System: Insert the tiles taken in a column of your shelf!".toUpperCase();
        Platform.runLater(() -> LivingRoom.writeInfos(info));
    }

    /**
     * Calls updateCommonGoal that will update the common goals.
     * @param mockCommonGoal most recent version of common goals.
     */
    public void updateCommonGoal(MockCommonGoal mockCommonGoal) {
        Platform.runLater(() -> LivingRoom.updateCommonGoal(mockCommonGoal.getEnumeration(), mockCommonGoal.getScoringToken().peek()));
    }

    /**
     * Calls outcomeMessage that will print the warning.
     * @param message warning to be print.
     */
    public void outcomeMessage(String message) {
        Platform.runLater(() -> LivingRoom.outcomeMessage(message));
    }

    /**
     * Calls refreshChat that update the chat specifically for a player that has rejoined after being crushed.
     * @param chat chat to show.
     */
    public void refreshChat(Stack<ChatMessage> chat) {
        List<ChatMessage> messageList = new ArrayList<>(chat);
        Platform.runLater(() -> LivingRoom.refreshChat(messageList));
    }

    public void outcomeInsertTiles() {
        Platform.runLater(LivingRoom::outcomeInsertTiles);
    }

    public void setUp() {
        Platform.runLater(LivingRoom::setUp);
    }
}
