package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

import static Client.ClientApp.*;

public class LoginScene extends Scene {
    private static GuiApplication app;
    private final TextField playerID;
    private final TextField createLobby;
    private final ComboBox<String> activeLobbies;
    private final Label activeGames;
    private final Button sendButton;
    private List<String> inputLobbies;


    public LoginScene(GuiApplication app, List<String> inputLobbies){
        super(new Pane(), 960, 750);

        setUserAgentStylesheet(STYLEPATH);

        LoginScene.app = app;

        this.inputLobbies = inputLobbies;

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
        if(inputLobbies != null && inputLobbies.size()!=0){
            for (String lobby : inputLobbies) {
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
        sendButton.setOnAction(e-> {
            try {
                handleSendButton();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

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

    private void handleSendButton() throws RemoteException {
        String username = playerID.getText();
        String lobbyName = createLobby.getText();
        String existingLobby = activeLobbies.getValue();
        if(Objects.equals(username, "")) {
            printError("Insert a username!");
            return;
        }
        if(Objects.equals(existingLobby, null) && Objects.equals(lobbyName, "")) {
            printError("Chose a lobby or create a new one!");
            return;
        }
        if(!Objects.equals(existingLobby, null) && !Objects.equals(lobbyName, "")) {
            printError("You can't both join a lobby and create a new one!");
            return;
        }

        if(existingLobby != null)
            network.login(username,existingLobby,view,network);
        else
            network.login(username,lobbyName,view,network);
    }

    public static void toLobbyScene(){
        Scene livingRoom = new LivingRoom(app);
        app.switchScene(livingRoom);
    }
    private void printError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(message.toUpperCase());

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
