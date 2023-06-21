package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import Enumeration.Color;
import Utils.Cell;
import Utils.Coordinates;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.Tile;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static Client.ClientApp.*;


public class LivingRoom extends Scene {
    private static GuiApplication app;

    private ImageView boardImage;
    private static GridPane gridBoard;
    private static MockModel mockModel;
    private static final HBox hBoxMyShelfAndCG = new HBox();
    private static VBox vBoxShelves;
    private static final List<ImageView> selectedTilesImg = new ArrayList<>();
    private static final List<Coordinates> selectedTiles = new ArrayList<>();


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
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Pane paneBase = new Pane();
                gridBoard.add(paneBase,j,i);
            }
        }

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
        shelf1img.setPreserveRatio(true);
        shelf2img.setPreserveRatio(true);
        shelf3img.setPreserveRatio(true);
        hBoxShelves.getChildren().addAll(shelf1,shelf2,shelf3);

        // hBoxMyShelfAndCG built dynamically in the method down in the code.
        vBoxShelves.getChildren().add(hBoxShelves);

        HBox mainHBox = new HBox();
        mainHBox.getChildren().addAll(boardPane,vBoxShelves);

        gridBoard.setOnMouseClicked(event -> {
            int tmp = 0;
            if (mockModel.getMockPlayers().size() == 2)
                tmp = 1;
            double mouseX = event.getX();
            double mouseY = event.getY();
            System.out.println("mouse x: "+ mouseX + ", mouse y: "+ mouseY);

            int colIndex = -1;
            int rowIndex = -1;

            double colStartX = 0;
            double colEndX = 70;
            for (int col = 0; col < 9; col++) {
                if (mouseX >= colStartX && mouseX <= colEndX) {
                    colIndex = col - tmp;
                    break;
                }
                colStartX += 70;
                colEndX += 70;
            }

            double rowStartY = 0;
            double rowEndY = 70;
            for (int row = 0; row < 9 + tmp; row++) {
                if (mouseY >= rowStartY && mouseY <= rowEndY) {
                    rowIndex = row - tmp;
                    break;
                }
                rowStartY += 70;
                rowEndY += 70;
            }

            if (colIndex >= 0 && rowIndex >= 0) {
                System.out.println("x: "+ rowIndex + ", y: "+ colIndex);
                selectedTiles.add(new Coordinates(rowIndex,colIndex));
                ImageView selectedImageView;
                selectedImageView = (ImageView) getPane(colIndex+tmp,rowIndex+tmp).getChildren().get(0);
                if (selectedImageView != null) {
                    selectedTilesImg.add(selectedImageView);
                }
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
        LivingRoom.mockModel = mockModel;
        int numPlayer = mockModel.getMockPlayers().size();
        ImageView image = new ImageView();
        int tmp = 0;
        if(numPlayer == 2)
            tmp = 1;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if (board[i][j].getStatus() && board[i][j].getTile() != null) {
                    String colorString = board[i][j].getTile().getColor().getCode();
                    image = choseImage(colorString);
                }
                Pane tmpPane = getPane(j+tmp,i+tmp);
                tmpPane.getChildren().add(image);
                //gridBoard.add(tmpPane,j+tmp,i+tmp);
            }
        }
    }

    private static Pane getPane(int j, int i) {
        List<Node> kids = gridBoard.getChildren();
        Pane res = null;
        for(Node n: kids){
            if(GridPane.getColumnIndex(n) == j && GridPane.getRowIndex(n) == i)
                res = (Pane)n;
        }
        return res;
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
        cg1img.setPreserveRatio(true);
        cg2img.setFitHeight(166);
        cg2img.setFitWidth(250);
        cg2img.setPreserveRatio(true);
        cg1.getChildren().add(cg1img);
        cg2.getChildren().add(cg2img);

        VBox vBoxCommonGoal = new VBox(10,cg1,cg2);

        Pane pGoalPane = new Pane();

        ImageView pGoalImg = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        pGoalImg.setPreserveRatio(true);
        pGoalImg.setFitWidth(250);
        pGoalImg.setFitHeight(300);

        GridPane pGoalGrid = new GridPane();
        pGoalGrid.setGridLinesVisible(true);
        pGoalGrid.setHgap(10);
        pGoalGrid.setVgap(2);
        pGoalGrid.setLayoutX(30);
        pGoalGrid.setLayoutY(20);
        pGoalGrid.prefWidthProperty().bind(pGoalImg.fitWidthProperty());
        pGoalGrid.prefHeightProperty().bind(pGoalImg.fitWidthProperty());

        for(int row=0;row<6;row++){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30);
            pGoalGrid.getRowConstraints().add(rowConstraints);
            pGoalGrid.addRow(row);
        }
        for(int col=0;col<5;col++){
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(30);
            pGoalGrid.getColumnConstraints().add(colConstraints);
            pGoalGrid.addColumn(col);
        }
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                Pane paneBase = new Pane();
                pGoalGrid.add(paneBase,j,i);
            }
        }
        pGoalGrid.setOnMouseClicked(event -> {
            try {
                network.selectTiles(localPlayer,selectedTiles);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        ImageView test = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Cornici1.1.png")));
        test.setPreserveRatio(true);
        test.setFitHeight(36);
        test.setFitWidth(27);

        // pGoalGrid.add(test,0,0);

        pGoalPane.getChildren().addAll(pGoalImg,pGoalGrid);


        hBoxMyShelfAndCG.setSpacing(50);
        hBoxMyShelfAndCG.getChildren().addAll(vBoxCommonGoal,pGoalPane);

        vBoxShelves.setSpacing(20);
        vBoxShelves.getChildren().add(hBoxMyShelfAndCG);
    }
    private static void printSelectedTiles() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Selected Tiles");
        alert.setHeaderText("Selected tiles: ");

        HBox hBoxPopUp = new HBox();
        hBoxPopUp.setPrefWidth(400);
        hBoxPopUp.setPrefHeight(200);
        hBoxPopUp.setSpacing(10);
        for (ImageView image : selectedTilesImg) {
            ImageView imageView = new ImageView(image.getImage());
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            hBoxPopUp.getChildren().add(imageView);
        }

        VBox vBoxMain = new VBox();

        Button send = new Button("send");
        send.setOnAction(e-> {
            try {
                insertTiles();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        send.setPrefHeight(40);
        send.setPrefWidth(60);

        ImageView playerShelf = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        playerShelf.setPreserveRatio(true);
        playerShelf.setFitWidth(200);
        playerShelf.setFitHeight(250);

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        for(int row=0;row<6;row++){
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30);
            grid.getRowConstraints().add(rowConstraints);
            grid.addRow(row);
        }
        for(int col=0;col<5;col++){
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(30);
            grid.getColumnConstraints().add(colConstraints);
            grid.addColumn(col);
        }
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                Pane paneBase = new Pane();
                grid.add(paneBase,j,i);
            }
        }

        grid.prefWidthProperty().bind(playerShelf.fitWidthProperty());
        grid.prefHeightProperty().bind(playerShelf.fitWidthProperty());
        grid.setLayoutX(20);
        grid.setLayoutY(10);
        grid.setHgap(2);
        grid.setVgap(5);

        Tile[][] pShelf = mockModel.getPlayer(localPlayer).getShelf();
        for(int i=0;i<pShelf.length;i++){
            for(int j=0;j<pShelf[0].length;j++){
               if( pShelf[i][j] != null ){
                   ImageView img = choseImage(pShelf[i][j].getTileColor().getCode());
                   getPane(i,j).getChildren().add(img);
               }
            }
        }

        Pane playerShelfPane = new Pane(grid,playerShelf);

        HBox arrows = new HBox();
        arrows.setPrefHeight(40);
        arrows.setPrefWidth(100);
        arrows.setSpacing(10);
        for(int i=0;i<5;i++){
            Pane pane = new Pane();
            pane.setStyle("-fx-border-color: red;");
            arrows.getChildren().add(pane);
            pane.setPrefHeight(20);
            pane.setPrefWidth(20);
        }

        vBoxMain.setSpacing(20);
        vBoxMain.getChildren().addAll(hBoxPopUp,arrows,playerShelfPane,send);

        alert.getDialogPane().setContent(vBoxMain);

        alert.showAndWait();
    }

    public static void outcomeSelectTiles(){
        printSelectedTiles();
        selectedTiles.clear();
        selectedTilesImg.clear();
    }

    public static void printError(String message) {
        selectedTilesImg.clear();
        selectedTiles.clear();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.show();

        alert.showAndWait();
    }
    public static void insertTiles() throws RemoteException {
        System.out.println("insert tiles");
    }
    private static ImageView choseImage(String colorString) {
        ImageView image;
        switch (colorString) {
            case "\u001b[42;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Gatti1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
            }
            case "\u001b[47;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Libri1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
            }
            case "\u001b[43;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Giochi1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
            }
            case "\u001b[44;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Cornici1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
            }
            case "\u001b[46;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Trofei1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
            }
            case "\u001b[45;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Piante1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
            }
            default -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Piante1.2.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
            }
        }
        return image;
    }

}