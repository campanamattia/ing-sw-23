package Client.View.Gui;

import Client.View.Gui.Scene.ConnectionScene;
import Client.View.Gui.Scene.LivingRoom;
import Client.View.Gui.Scene.LobbyScene;
import Client.View.Gui.Scene.LoginScene;
import Utils.Cell;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static Client.ClientApp.network;
import static Client.ClientApp.view;

public class GuiApplication extends Application {

    private Stage primaryStage;
    private List<String> activeLobbies = new ArrayList<>();

    private Integer lobbySize;
    private Boolean firstPlayer = false;
    private Cell[][] board;

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
    public List<String> updateLobbies(){
        return new ArrayList<>(this.activeLobbies);
    }

    public void setLobbies(List<String> lobbies){
        this.activeLobbies = lobbies;
    }

    public Integer getLobbySize(){
        return this.lobbySize;
    }

    public void setLobbySize(Integer lobbySize){
        this.lobbySize = lobbySize;
    }

    public void outcomeLogin(){
        Platform.runLater(LoginScene::toLobbyScene);
    }

    public void askLobbySize(){
        Platform.runLater(LivingRoom::toLobbySize);
    }

    public void askPlayerInfo(){
        Platform.runLater(()->{
            ConnectionScene.toLoginScene(this.activeLobbies);
        });
    }
    public void showBoard(Cell[][] board){
        this.board = board;
        Platform.runLater(()->{
            LivingRoom.showBoard(board);
        });
    }

}
