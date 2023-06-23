package Client.View.Gui.Scene;
import Client.View.Gui.GuiApplication;
import Utils.MockObjects.MockModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static Client.ClientApp.STYLEPATH;

public class ChatScene extends Scene{
    private static GuiApplication app;
    private static MockModel mockModel;

    public ChatScene(GuiApplication app) {

        super(new Pane(), 960, 750);

        ChatScene.app = app;

        setUserAgentStylesheet(STYLEPATH);

        Label tmp = new Label("setting up");
        Button back = new Button("back");
        back.setOnMouseClicked(e->backToLivingRoom());
        VBox vBox = new VBox(tmp,back);

        setRoot(vBox);
    }

    private void backToLivingRoom() {
        LivingRoom livingRoom = new LivingRoom(app);
        app.switchScene(livingRoom);
        app.updateMockModel(mockModel);
        app.showBoard(mockModel.getMockBoard().getBoard());
        app.setFromChat(true);
        app.showShelves();
    }
    public static void updateMockModel(MockModel mockmodel){
        mockModel = mockmodel;
    }
}
