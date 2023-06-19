package Client.View.Gui.Scene;

import Client.View.Gui.Gui;
import Client.View.Gui.GuiApplication;
import Utils.Cell;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import static Client.ClientApp.STYLEPATH;
import static Client.ClientApp.guiApplication;


public class LivingRoom extends Scene {
    private static GuiApplication app;

    private ImageView boardImage;
    private static GridPane gridBoard;
    private Pane boardPane;

    public LivingRoom(GuiApplication app) {

        super(new Pane(), 1366, 768);
        setUserAgentStylesheet(STYLEPATH);

        LivingRoom.app = app;

        String boardImg = String.valueOf(GuiApplication.class.getResource("/img/boards/livingroom.png"));
        boardImage = new ImageView(boardImg);
        boardStyle(boardImage);

        boardPane = new Pane();
        gridBoard = new GridPane();
        gridBoard.prefWidthProperty().bind(boardImage.fitWidthProperty());
        gridBoard.prefHeightProperty().bind(boardImage.fitWidthProperty());
        // gridBoard.setGridLinesVisible(true);

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

        boardPane.getChildren().addAll(boardImage,gridBoard);

        HBox mainHBox = new HBox();
        mainHBox.getChildren().add(boardPane);

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

    public static void showBoard(Cell[][] board){
        ImageView image;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if (board[i][j].getStatus() && board[i][j].getTile() != null) {
                    String colorString = board[i][j].getTile().getColor().getCode();
                    switch (colorString) {
                        case "\u001b[42;1m" -> {
                            image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Gatti1.1.png")));
                            image.setFitHeight(60);
                            image.setFitWidth(60);
                        }
                        case "\u001b[47;1m" -> {
                            image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Libri1.1.png")));
                            image.setFitHeight(60);
                            image.setFitWidth(60);
                        }
                        case "\u001b[43;1m" -> {
                            image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Giochi1.1.png")));
                            image.setFitHeight(60);
                            image.setFitWidth(60);
                        }
                        case "\u001b[44;1m" -> {
                            image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Cornici1.1.png")));
                            image.setFitHeight(60);
                            image.setFitWidth(60);
                        }
                        case "\u001b[46;1m" -> {
                            image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Trofei1.1.png")));
                            image.setFitHeight(60);
                            image.setFitWidth(60);
                        }
                        case "\u001b[45;1m" -> {
                            image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Piante1.1.png")));
                            image.setFitHeight(60);
                            image.setFitWidth(60);
                        }
                        default -> {
                            image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Piante1.2.png")));
                            image.setFitHeight(60);
                            image.setFitWidth(60);
                        }

                    }
                    gridBoard.add(image,i,j);
                }
            }
        }
    }

}