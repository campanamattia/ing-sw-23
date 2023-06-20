package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import Utils.Cell;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;


import static Client.ClientApp.*;


public class LivingRoom extends Scene {
    private static GuiApplication app;

    private ImageView boardImage;
    private static GridPane gridBoard;
    private static MockModel mockModel;
    private static final HBox hBoxMyShelfAndCG = new HBox();
    private static VBox vBoxShelves;


    public LivingRoom(GuiApplication app) {

        super(new Pane(), 1416, 768);
        setUserAgentStylesheet(STYLEPATH);

        LivingRoom.app = app;

        String boardImg = String.valueOf(GuiApplication.class.getResource("/img/boards/livingroom.png"));
        boardImage = new ImageView(boardImg);
        boardStyle(boardImage);

        Pane boardPane = new Pane();
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
        gridBoard.setDisable(false);

        boardPane.getChildren().addAll(boardImage,gridBoard);

        vBoxShelves = new VBox();

        HBox hBoxShelves = new HBox();
        Pane shelf1 = new Pane();
        Pane shelf2 = new Pane();
        Pane shelf3 = new Pane();
        ImageView shelf1img = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        ImageView shelf2img = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        ImageView shelf3img = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        shelf1img.setFitWidth(200);
        shelf1img.setFitHeight(250);
        shelf2img.setFitWidth(200);
        shelf2img.setFitHeight(250);
        shelf3img.setFitWidth(200);
        shelf3img.setFitHeight(250);
        shelf1.getChildren().add(shelf1img);
        shelf2.getChildren().add(shelf2img);
        shelf3.getChildren().add(shelf3img);
        hBoxShelves.setSpacing(20);
        shelf1img.setLayoutX(10);
        shelf1img.setLayoutY(20);
        shelf2img.setLayoutX(10);
        shelf2img.setLayoutY(20);
        shelf3img.setLayoutX(10);
        shelf3img.setLayoutY(20);
        hBoxShelves.getChildren().addAll(shelf1,shelf2,shelf3);

        // hBoxMyShelfAndCG built dynamically in the method down in the code.
        vBoxShelves.getChildren().add(hBoxShelves);

        HBox mainHBox = new HBox();
        mainHBox.getChildren().addAll(boardPane,vBoxShelves);

        gridBoard.setOnMouseClicked(event -> {
            int tmp = 1;
            if (mockModel.getMockPlayers().size() == 2)
                tmp = 0;
            double mouseX = event.getX();
            double mouseY = event.getY();

            int colIndex = -1;
            int rowIndex = -1;

            for (int col = 0; col < 9; col++) {
                double colStartX = 51 + col * 70;
                double colEndX = colStartX + 60;
                if (mouseX >= colStartX && mouseX <= colEndX) {
                    colIndex = col + tmp;
                    break;
                }
            }
            for (int row = tmp; row < 9 + tmp; row++) {
                double rowStartY = 55 + (row - tmp) * 70;
                double rowEndY = rowStartY + 60;
                if (mouseY >= rowStartY && mouseY <= rowEndY) {
                    rowIndex = row;
                    break;
                }
            }

            if (colIndex >= 0 && rowIndex >= 0) {
                System.out.println("Row: " + rowIndex);
                System.out.println("Column: " + colIndex);
            }
        });
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

    public static void showBoard(Cell[][] board,MockModel mockModel){
        Pane imgPane;
        LivingRoom.mockModel = mockModel;
        int numPlayer = mockModel.getMockPlayers().size();
        ImageView image;
        int tmp = 0;
        if(numPlayer == 2)
            tmp = 1;
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
                    imgPane = new Pane(image);
                    gridBoard.add(imgPane,j+tmp,i+tmp);
                }
            }
        }
    }

    public static void updateCommonAndPersonalGoal(){
        MockCommonGoal mockCommonGoal1 = LivingRoom.mockModel.getMockCommonGoal().get(0);
        int numberCGoal1 = mockCommonGoal1.getEnumeration();
        MockCommonGoal mockCommonGoal2 = LivingRoom.mockModel.getMockCommonGoal().get(1);
        int numberCGoal2 = mockCommonGoal2.getEnumeration();

        // int numPlayer = mockModel.getMockPlayers().size();

        Pane cg1 = new Pane();
        Pane cg2 = new Pane();
        ImageView cg1img = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/common_goal_cards/"+numberCGoal1+".jpg")));
        ImageView cg2img = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/common_goal_cards/"+numberCGoal2+".jpg")));
        cg1img.setFitHeight(166);
        cg1img.setFitWidth(250);
        cg2img.setFitHeight(166);
        cg2img.setFitWidth(250);
        cg1.getChildren().add(cg1img);
        cg2.getChildren().add(cg2img);

        VBox vBoxCommonGoal = new VBox(10,cg1,cg2);

        Pane pGoalPane = new Pane();
        ImageView pGoalImg = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        pGoalImg.setFitWidth(250);
        pGoalImg.setFitHeight(300);
        pGoalPane.getChildren().add(pGoalImg);

        // GridPane pGoalGrid = new GridPane();

        hBoxMyShelfAndCG.setSpacing(50);
        hBoxMyShelfAndCG.getChildren().addAll(vBoxCommonGoal,pGoalPane);

        vBoxShelves.setSpacing(20);
        vBoxShelves.getChildren().add(hBoxMyShelfAndCG);
    }
}