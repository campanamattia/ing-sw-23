package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static Client.ClientApp.STYLEPATH;

public class ConnectionScene extends Scene {

    private final GuiApplication app;

    public ConnectionScene(GuiApplication app) {

        super(new Pane(), 960, 750);

        setUserAgentStylesheet(STYLEPATH);

        this.app = app;

        Label label = new Label("Chose the type of connection: ");

        Button rmiButton = new Button("RMI");

        Button socketButton = new Button("SOCKET");

        rmiButton.setOnAction(e -> handleRMIClick());
        socketButton.setOnAction(e -> handleSocketClick());

        VBox backgroundBox = new VBox();
        backgroundBox.setSpacing(40);
        backgroundBox.setAlignment(Pos.CENTER);

        HBox hBoxButtons = new HBox(40,rmiButton,socketButton);
        hBoxButtons.setAlignment(Pos.CENTER);

        backgroundBox.getChildren().addAll(label, hBoxButtons);

        setRoot(backgroundBox);
    }

    private void handleRMIClick() {
        System.out.println("Enabling RMI connection...");

    }

    private void handleSocketClick() {
        System.out.println("Enabling SOCKET connection...");
    }
}
