package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;

import static Client.ClientApp.*;

public class LobbyScene extends Scene {
    private final GuiApplication app;

    /**
     * Class constructor.
     */
    public LobbyScene(GuiApplication app) {
        super(new Pane(), 960, 750);
        setUserAgentStylesheet(STYLEPATH);

        this.app = app;

        Label label = new Label("Setup Lobby: ");
        label.getStyleClass().add("label-title");

        VBox backGroundVBox = new VBox();
        backGroundVBox.setSpacing(30);
        backGroundVBox.setAlignment(Pos.CENTER);
        backGroundVBox.getChildren().add(label);
        for(int i=2;i<=4;i++){
            Button button = new Button(String.valueOf(i));
            button.setId(String.valueOf(i));
            button.getStyleClass().add("button-lobby-size");
            button.setOnAction(e-> {
                try {
                    handleJoinGameButton(Integer.parseInt(button.getId()));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            });
            backGroundVBox.getChildren().add(button);
        }

        setRoot(backGroundVBox);

    }
    private void handleJoinGameButton(int size) throws RemoteException {
        network.setLobbySize(localPlayer,lobbyID,size);

        Scene livingRoom = new LivingRoom(app);
        app.switchScene(livingRoom);
    }
}
