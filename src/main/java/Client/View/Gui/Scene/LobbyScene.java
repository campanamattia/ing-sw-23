package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;

import static Client.ClientApp.*;

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
        joinGameButton.setOnAction(e -> {
            try {
                handleJoinGameButton();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox backgroundBox = new VBox();
        backgroundBox.setSpacing(40);
        backgroundBox.setAlignment(Pos.CENTER);
        backgroundBox.getChildren().addAll(label, lobbySize,joinGameButton);

        setRoot(backgroundBox);

    }
    private void handleJoinGameButton() throws RemoteException {
        Integer selectedLobbySize = lobbySize.getValue();
        if(selectedLobbySize != null)
            network.setLobbySize(localPlayer,lobbyID,selectedLobbySize);
        else {
            printError();
            return;
        }
        Scene livingRoom = new LivingRoom(app);
        app.switchScene(livingRoom);
    }
    private void printError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Select the size of the lobby!".toUpperCase());

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().clear();

        // Set OK button
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Set the owner window
        alert.initOwner(this.getWindow());

        // Show the alert and wait for user response
        alert.showAndWait();
    }

}
