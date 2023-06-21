package Client.View.Gui;

import Client.View.Cli.LightController;
import Client.View.Gui.Scene.ConnectionScene;
import Client.View.Gui.Scene.LivingRoom;
import Client.View.Gui.Scene.LoginScene;
import Utils.Cell;
import Utils.MockObjects.MockModel;
import Utils.Tile;
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
    public void showBoard(Cell[][] board, MockModel mockModel){
        Platform.runLater(()-> LivingRoom.showBoard(board,mockModel));
    }
    public void showShelves(){
        Platform.runLater(LivingRoom::updateCommonAndPersonalGoal);
    }
    public void outcomeSelectTiles(List<Tile> selectedTiles){
        Platform.runLater(LivingRoom::outcomeSelectTiles);
    }
    public void outcomeException(Exception e){
        Platform.runLater(()->
            LivingRoom.printError(e.getMessage())
        );
    }
}
