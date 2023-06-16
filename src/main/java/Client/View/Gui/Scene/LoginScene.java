package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.util.List;

import static Client.ClientApp.*;

public class LoginScene extends Scene {
    private final GuiApplication app;
    private final TextField playerID;
    private final TextField createLobby;
    private final ComboBox<String> activeLobbies;
    private final Label activeGames;
    private final Button sendButton;
    private String outcomeLogin;


    public LoginScene(GuiApplication app){
        super(new Pane(), 960, 750);

        setUserAgentStylesheet(STYLEPATH);

        this.app = app;

        Label label = new Label("Login: ");
        label.getStyleClass().add("label-title");

        VBox backgroundBox = new VBox();
        backgroundBox.setSpacing(80);
        backgroundBox.setAlignment(Pos.TOP_CENTER);

        activeLobbies = new ComboBox<>();
        createLobby = new TextField();
        createLobby.setPromptText("create a new lobby: ");
        createLobby.getStyleClass().add("text.field");
        createLobby.setMaxWidth(400);
        List<String> lobbies = app.updateLobbies();
        if(lobbies.size()!=0){
            for (String lobby : lobbies) {
                activeLobbies.getItems().add(lobby);
            }
        }else{
            activeLobbies.setPromptText("No active lobbies, create a new one");
        }
        activeGames = new Label("Active games: ");

        playerID = new TextField();
        playerID.setPromptText("Insert username: ");
        playerID.getStyleClass().add("text-field");
        playerID.setMaxWidth(400);

        sendButton = new Button("Send");
        sendButton.setOnAction(e-> handleSendButton());

        VBox vBoxGameAndLobbies = new VBox();
        vBoxGameAndLobbies.getChildren().addAll(activeLobbies,createLobby,activeGames);
        vBoxGameAndLobbies.setSpacing(50);

        VBox vBoxInput = new VBox();
        vBoxInput.getChildren().addAll(playerID);
        vBoxInput.setSpacing(50);

        HBox hBoxMain = new HBox(100,vBoxGameAndLobbies,vBoxInput);
        hBoxMain.setAlignment(Pos.CENTER);

        backgroundBox.setAlignment(Pos.TOP_CENTER);
        backgroundBox.getChildren().addAll(label,hBoxMain,sendButton);



        setRoot(backgroundBox);
    }

    private void handleSendButton(){
        String username = playerID.getText();
        String lobbyName = createLobby.getText();
        guiApplication.setPlayerInfo(username,lobbyName);
        if(app.getFirstPlayer()) {
            app.setFirstPlayer(false);
            Scene lobbyScene = new LobbyScene(app);
            app.switchScene(lobbyScene);
        }else{
            Scene livingRoom = new LivingRoom(app);
            app.switchScene(livingRoom);
        }
    }
}
