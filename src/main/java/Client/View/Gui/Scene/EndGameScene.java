package Client.View.Gui.Scene;

import Utils.Rank;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

import static Client.ClientApp.STYLEPATH;

public class EndGameScene extends Scene {
    private static List<Rank> ranks;

    /**
     * Class constructor.
     */
    public EndGameScene() {
        super(new Pane(), 960, 750);
        setUserAgentStylesheet(STYLEPATH);

        VBox rank = new VBox();
        Label playerID;
        for (Rank value : ranks) {
            if(value == null)
                break;
            playerID = new Label(value.getPlayerID());
            rank.getChildren().add(playerID);
        }

        setRoot(rank);
    }

    /**
     * Setter of ranks of the player.
     * @param ranks ranks of the players.
     */
    public static void setRanks(List<Rank> ranks) {
        EndGameScene.ranks = ranks;
    }
}
