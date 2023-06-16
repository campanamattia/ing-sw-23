package Client.View.Gui;

import Client.View.Gui.Scene.ConnectionScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static Client.ClientApp.network;

public class GuiApplication extends Application {

    private Stage primaryStage;
    private List<String> activeLobbies = new ArrayList<>();

    private List<String> playerInfo;

    private Integer lobbySize;

    private Gui gui;
    private String outcomeLogin = "Not logged";
    private Boolean firstPlayer = false;

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
        primaryStage.setScene(connectionScene);
        primaryStage.show();
    }

    public void switchScene(Scene scene) {
        primaryStage.setScene(scene);
    }
    public List<String> updateLobbies(){
        List<String> activeLobbies = new ArrayList<>(this.activeLobbies);
        return activeLobbies;
    }

    public void setPlayerInfo(String username,String lobby){
        playerInfo = new ArrayList<>();
        playerInfo.add(username);
        playerInfo.add(lobby);
    }
    public List<String> getPlayerInfo(){
        return playerInfo;
    }

    public void setLobbies(List<String> lobbies){
        this.activeLobbies = lobbies;
    }
    public void updateGui(Gui gui){
        this.gui = gui;
    }

    public Integer getLobbySize(){
        return this.lobbySize;
    }

    public void setLobbySize(Integer lobbySize){
        this.lobbySize = lobbySize;
    }

    public void outcomeLogin(String outcome){
        this.outcomeLogin = outcome;
    }

    public String printLogin(){
        return this.outcomeLogin;
    }

    public Boolean getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
}
