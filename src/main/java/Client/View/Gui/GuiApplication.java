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

    public GuiApplication() {
        if (!javaFxLaunched) { // First time
            Platform.setImplicitExit(false);
            new Thread(()->Application.launch(GuiApplication.class)).start();
            javaFxLaunched = true;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        Scene connectionScene = new ConnectionScene(this);
        this.switchScene(connectionScene);
        primaryStage.show();
    }

    public void switchScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public void setLobbies(List<String> lobbies){
        this.activeLobbies = lobbies;
    }

    public void setActiveGames(List<String> activeGames) {
        this.activeGames = activeGames;
    }

    public void outcomeLogin(){
        Platform.runLater(LoginScene::toLobbyScene);
    }

    public void askLobbySize(){
        Platform.runLater(LivingRoom::toLobbySize);
    }

    public void askPlayerInfo(){
        Platform.runLater(()-> ConnectionScene.toLoginScene(this.activeLobbies,this.activeGames));
    }

    private boolean firstBoard = true;

    public void showBoard(Cell[][] board){
        if(firstBoard) {
            firstBoard = false;
            Platform.runLater(() -> LivingRoom.showBoard(board));
        }
    }

    private boolean firstShelves = true;

    public void showShelves(){
        if(firstShelves) {
            System.out.println("first ever print shelves!");
            firstShelves = false;
            Platform.runLater(LivingRoom::showCommonAndShelves);
        }else{
            Platform.runLater(LivingRoom::updateShelves);
        }
    }
    public void outcomeSelectTiles(){
        Platform.runLater(LivingRoom::outcomeSelectTiles);
    }
    public void outcomeException(Exception e){
        Platform.runLater(()->
            LivingRoom.printError(e.getMessage())
        );
    }
    public void updateBoard(Cell[][] board){
        Platform.runLater(()->
                LivingRoom.updateBoard(board)
        );
    }
    public void updateMockModel(MockModel mockModel){
        Platform.runLater(()->
                LivingRoom.updateMockModel(mockModel)
        );
    }

    public void endGame(List<Rank> leaderboard) {
        Platform.runLater(()->
                LivingRoom.endGame(leaderboard)
        );
    }
    public void newMessageChat(ChatMessage message){
        Platform.runLater(()->
                LivingRoom.newMessageChat(message)
        );
    }

    public void writeCurrentPlayer(String playerID) {
        String personalMessage = "System: Select from 1 to 3 tiles!".toUpperCase();
        if(!localPlayer.equals(playerID))
            personalMessage = "";
        String info = (!playerID.equals(localPlayer)) ? ("System: " + playerID + "'s turn!\n" + personalMessage).toUpperCase() : "System: YOUR TURN".toUpperCase();
        Platform.runLater(()->LivingRoom.writeInfos(info));
    }

    public void updateHelp(){
        String info = "System: Insert the tiles taken in a column of your shelf!".toUpperCase();
        Platform.runLater(()->LivingRoom.writeInfos(info));
    }

    public void updateCommonGoal(MockCommonGoal mockCommonGoal) {
        Platform.runLater(()->LivingRoom.updateCommonGoal(mockCommonGoal.getEnumeration(),mockCommonGoal.getScoringToken().peek()));
    }

    public void outcomeMessage(String message) {
        Platform.runLater(()->LivingRoom.outcomeMessage(message));
    }

    public void refreshChat(Stack<ChatMessage> chat) {
        List<ChatMessage> messageList = new ArrayList<>(chat);
        Platform.runLater(()->LivingRoom.refreshChat(messageList));
    }

    public void lastRound() {
        Platform.runLater(LivingRoom::lastRound);
    }
}
