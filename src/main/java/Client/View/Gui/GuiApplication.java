package Client.View.Gui;

import Client.View.Gui.Scene.ConnectionScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiApplication extends Application {

    private Stage primaryStage;

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
}
