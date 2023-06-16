package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static Client.ClientApp.STYLEPATH;
import static Client.ClientApp.guiApplication;

public class LobbyScene extends Scene {
    private final GuiApplication app;
    private final ComboBox<Integer> lobbySize;

    public LobbyScene(GuiApplication app) {
        super(new Pane(), 960, 750);
        setUserAgentStylesheet(STYLEPATH);

        this.app = app;

        Label label = new Label("Setup Lobby: ");
        label.getStyleClass().add("label-title");

        lobbySize = new ComboBox<>();
        lobbySize.setPromptText("Insert the number of players: ");
        lobbySize.getItems().addAll(2,3,4);

        Button joinGameButton = new Button("Join Game!");
        joinGameButton.setOnAction(e ->handleJoinGameButton());

        VBox backgroundBox = new VBox();
        backgroundBox.setSpacing(40);
        backgroundBox.setAlignment(Pos.CENTER);
        backgroundBox.getChildren().addAll(label, lobbySize,joinGameButton);

        setRoot(backgroundBox);

    }
    private void handleJoinGameButton(){
        Integer selectedLobbySize = lobbySize.getValue();
        guiApplication.setLobbySize(selectedLobbySize);
        Scene livingRoom = new LivingRoom(app);
        app.switchScene(livingRoom);
    }

}
