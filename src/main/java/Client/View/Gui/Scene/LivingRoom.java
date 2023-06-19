package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import static Client.ClientApp.STYLEPATH;


public class LivingRoom extends Scene {
    private static GuiApplication app;

    private ImageView boardImage;
    private GridPane gridBoard;
    private Pane board;

    public LivingRoom(GuiApplication app) {

        super(new Pane(), 1366, 768);
        setUserAgentStylesheet(STYLEPATH);

        LivingRoom.app = app;

        String boardImg = String.valueOf(GuiApplication.class.getResource("/img/boards/livingroom.png"));
        boardImage = new ImageView(boardImg);
        boardStyle(boardImage);

        board = new Pane();
        gridBoard = new GridPane();
        gridBoard.prefWidthProperty().bind(boardImage.fitWidthProperty());
        gridBoard.prefHeightProperty().bind(boardImage.fitWidthProperty());
        gridBoard.setGridLinesVisible(true);

        for(int row=0;row<9;row++){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(60);
            gridBoard.getRowConstraints().add(rowConstraints);
            gridBoard.addRow(row);
        }
        for(int col=0;col<9;col++){
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(60);
            gridBoard.getColumnConstraints().add(colConstraints);
            gridBoard.addColumn(col);
        }

        ImageView test = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Cornici1.1.png")));
        test.setFitHeight(60);
        test.setFitWidth(60);

        gridBoard.setLayoutX(51);
        gridBoard.setLayoutY(55);
        gridBoard.setHgap(10);
        gridBoard.setVgap(10);

        board.getChildren().addAll(boardImage,gridBoard);

        HBox mainHBox = new HBox();
        mainHBox.getChildren().add(board);

        setRoot(mainHBox);

    }

    public static void toLobbySize(){
        Scene lobbySize = new LobbyScene(app);
        app.switchScene(lobbySize);
    }

    private void boardStyle(ImageView boardImage){
        this.boardImage = boardImage;
        boardImage.setPreserveRatio(true);
        boardImage.setFitWidth(690);
        boardImage.setFitHeight(768);
        boardImage.setLayoutX(20);
        boardImage.setLayoutY(20);
    }

}