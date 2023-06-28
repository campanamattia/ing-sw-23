package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static Client.ClientApp.*;

public class LoginScene extends Scene {
    private static GuiApplication app;
    private final TextField playerID;
    private final TextField createLobby;
    private static final String TAB = "   ";

    /**
     * Class constructor.
     */
    public LoginScene(GuiApplication app, List<Map<String, String>> lobbyInfo){
        super(new Pane(), 960, 750);

        setUserAgentStylesheet(STYLEPATH);

        LoginScene.app = app;

        Label label = new Label("Login: ");
        label.getStyleClass().add("label-title");

        VBox backgroundBox = new VBox();
        backgroundBox.setSpacing(80);
        backgroundBox.setAlignment(Pos.TOP_CENTER);

        VBox vBoxGameAndLobbies = new VBox();
        vBoxGameAndLobbies.setSpacing(20);

        createLobby = new TextField();
        createLobby.setPromptText("create/join a lobby/game: ");
        createLobby.getStyleClass().add("text-field");
        if(lobbyInfo != null && lobbyInfo.get(0).keySet().size() != 0){
            for (String lobby : lobbyInfo.get(0).keySet()) {
                String toShow = "LobbyID: " + lobby + TAB + " Waiting Room: " + lobbyInfo.get(0).get(lobby);
                Font customFont = Font.loadFont(String.valueOf(GuiApplication.class.getResource("/font/Poppins-Regular.ttf")),15);
                Label lobbyLabel = new Label(toShow);
                lobbyLabel.setFont(customFont);
                Label activeLobbies = new Label(toShow);
                vBoxGameAndLobbies.getChildren().add(activeLobbies);
            }
        }else{
            Label activeLobbies = new Label("No active lobbies, create a new one");
            vBoxGameAndLobbies.getChildren().add(activeLobbies);
        }

        if(lobbyInfo != null && lobbyInfo.get(1).keySet().size() != 0){
            for (String game : lobbyInfo.get(1).keySet()) {
                String toShow = "LobbyID: " + game + TAB + " Waiting Room: " + lobbyInfo.get(1).get(game);
                Font customFont = Font.loadFont(String.valueOf(GuiApplication.class.getResource("/font/Poppins-Regular.ttf")),15);
                Label gameLabel = new Label(toShow);
                gameLabel.setFont(customFont);
                Label activeGames = new Label(toShow);
                vBoxGameAndLobbies.getChildren().add(activeGames);
            }
        }else{
            Label activeGames = new Label("No active games, create a new one");
            vBoxGameAndLobbies.getChildren().add(activeGames);
        }

        playerID = new TextField();
        playerID.setPromptText("Insert username: ");
        playerID.getStyleClass().add("text-field");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e-> {
            try {
                handleSendButton();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox vBoxInput = new VBox();
        vBoxInput.getChildren().addAll(playerID,createLobby);
        vBoxInput.setSpacing(50);

        HBox hBoxMain = new HBox(100,vBoxGameAndLobbies,vBoxInput);
        hBoxMain.setAlignment(Pos.CENTER);

        backgroundBox.setAlignment(Pos.TOP_CENTER);
        backgroundBox.getChildren().addAll(label,hBoxMain, sendButton);



        setRoot(backgroundBox);
    }

    private void handleSendButton() throws RemoteException {
        String username = playerID.getText();
        String lobbyName = createLobby.getText();

        if(lobbyName != null)
            network.login(username,lobbyName,view,network);
    }

    /**
     * Calls switchScene and set as an active scene the Living room Scene.
     */
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
