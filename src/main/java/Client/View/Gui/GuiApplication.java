package Client.View.Gui;

import Client.View.Gui.Scene.ConnectionScene;
import Client.View.Gui.Scene.LivingRoom;
import Client.View.Gui.Scene.LoginScene;
import Utils.Cell;
import Utils.ChatMessage;
import Utils.MockObjects.MockModel;
import Utils.Rank;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GuiApplication extends Application {

    private Stage primaryStage;
    private List<String> activeLobbies = new ArrayList<>();

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

    public void outcomeLogin(){
        Platform.runLater(LoginScene::toLobbyScene);
    }

    public void askLobbySize(){
        Platform.runLater(LivingRoom::toLobbySize);
    }

    public void askPlayerInfo(){
        Platform.runLater(()-> ConnectionScene.toLoginScene(this.activeLobbies));
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
}
