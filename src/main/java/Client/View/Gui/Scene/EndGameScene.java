package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import Utils.Rank;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;


public class EndGameScene extends Scene {
    private static VBox rank_1_2;
    private static VBox rank_3_4;
    private static HBox mainHBox;
    private static VBox mainVBox;
    GuiApplication app;

    /**
     * Class constructor.
     */
    public EndGameScene(GuiApplication app) {
        super(new Pane(), 960, 750);
        setUserAgentStylesheet(String.valueOf(GuiApplication.class.getResource("/css/endgame.css")));
        this.app = app;

        mainHBox = new HBox();
        mainHBox.setAlignment(Pos.CENTER);
        mainVBox = new VBox();
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.getStyleClass().add("wallpaper");
        rank_1_2 = new VBox();
        rank_1_2.setAlignment(Pos.CENTER);
        rank_3_4 = new VBox();
        rank_3_4.setAlignment(Pos.CENTER);

        setRoot(mainVBox);
    }

    /**
     * Setter of ranks of the player.
     * @param ranks ranks of the players.
     */
    public static void updateRanks(List<Rank> ranks) {
        Font customFont = Font.loadFont(String.valueOf(GuiApplication.class.getResource("/font/Poppins-Regular.ttf")),30);
        Label winner = new Label("THE WINNER IS: " + ranks.get(0).getPlayerID().toUpperCase());
        winner.setFont(customFont);
        winner.setStyle("-fx-text-fill: white;");
        mainVBox.getChildren().add(winner);
        System.out.println(ranks);
        int count = 0;
        for (Rank value : ranks) {
            if(value == null)
                break;
            Label playerID = new Label(value.toString());
            playerID.setFont(customFont);
            playerID.setStyle("-fx-text-fill: white;");
            count++;
            if(count < 2)
                rank_1_2.getChildren().add(playerID);
            else
                rank_3_4.getChildren().add(playerID);
        }
        if(rank_3_4.getChildren().size() == 0)
            mainVBox.getChildren().add(rank_1_2);
        else{
            mainHBox.getChildren().addAll(rank_1_2,rank_3_4);
            mainVBox.getChildren().add(mainHBox);
        }

    }
}
