package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import static Client.ClientApp.STYLEPATH;



public class LivingRoom extends Scene {
    private final GuiApplication app;
    private final Label livingRoom;


    public LivingRoom(GuiApplication app) {
        super(new Pane(), 960, 750);
        setUserAgentStylesheet(STYLEPATH);

        this.app = app;

        livingRoom = new Label("Setting up...");
        livingRoom.setAlignment(Pos.CENTER);

        setRoot(livingRoom);

    }

}