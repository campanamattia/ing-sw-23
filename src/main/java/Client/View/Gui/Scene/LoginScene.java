package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static Client.ClientApp.*;

/**
 * The `LoginScene` class represents the scene where players can log in and join lobbies or create a new lobby.
 * It extends the JavaFX `Scene` class and provides a UI for entering a username,
 * creating a lobby/game, and displaying active lobbies/games.
 */
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
        createLobby.setPromptText("Insert lobby/game: ");
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
                String toShow = "GameID: " + game + TAB + " Waiting Room: " + lobbyInfo.get(1).get(game);
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
        playerID.setPrefWidth(300);

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e-> {
            try {
                handleSendButton();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox hBoxInput = new HBox();
        hBoxInput.getChildren().addAll(createLobby,playerID);
        hBoxInput.setSpacing(50);
        hBoxInput.setAlignment(Pos.CENTER);

        vBoxGameAndLobbies.setAlignment(Pos.CENTER);
        VBox vBoxMain = new VBox(100,hBoxInput,vBoxGameAndLobbies);
        vBoxMain.setAlignment(Pos.CENTER);

        backgroundBox.setAlignment(Pos.TOP_CENTER);
        backgroundBox.getChildren().addAll(label,vBoxMain, sendButton);



        setRoot(backgroundBox);
    }

    private void handleSendButton() throws RemoteException {
        String username = playerID.getText();
        String lobbyName = createLobby.getText();

        if(username.equals("")){
            app.printError("INSERT A USERNAME");
            return;
        }

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
}
