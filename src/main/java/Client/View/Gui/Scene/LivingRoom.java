package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import Utils.Cell;
import Utils.Coordinates;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.Tile;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private static GridPane pGoalGrid;
    private static MockModel mockModel;
    private static final HBox hBoxMyShelfAndCG = new HBox();
    private static VBox vBoxShelves;
    private static final List<ImageView> selectedTilesImg = new ArrayList<>();
    private static final List<Coordinates> selectedTiles = new ArrayList<>();
    private static VBox leftSide;
    private static VBox notifies;
    private static TextField orderTile;
    private static TextField column;
    private static final List<GridPane> othersShelves = new ArrayList<>();

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

        leftSide = new VBox();
        leftSide.getChildren().add(boardPane);

        HBox mainHBox = new HBox();
        mainHBox.getChildren().addAll(leftSide,vBoxShelves);

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
                Pane selectedPane = getPane(gridBoard, colIndex + tmp, rowIndex + tmp);
                if (selectedPane != null) {
                    ImageView selectedImageView = (ImageView) selectedPane.getChildren().get(0);
                    if (selectedImageView != null) {
                        selectedTilesImg.add(selectedImageView);
                    }
                }
            }
        });
        setRoot(mainHBox);
    }

    public static void toLobbySize(){
        Scene lobbySize = new LobbyScene(app);
        app.switchScene(lobbySize);
    }

    public static void updateMockModel(MockModel mockmodel) {
        mockModel = mockmodel;
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
        Label currentPlayer = new Label(mockModel.getCurrentPlayer() + "'s turn!");
        notifies = new VBox();
        notifies.setVisible(true);
        notifies.setPrefWidth(150);
        notifies.setPrefHeight(20);
        notifies.setLayoutX(20);
        notifies.getChildren().add(currentPlayer);
        leftSide.getChildren().add(notifies);
        int numPlayer = mockModel.getMockPlayers().size();
        ImageView image;
        int tmp = 0;
        if(numPlayer == 2)
            tmp = 1;
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if (board[i][j].getStatus() && board[i][j].getTile() != null) {
                    String colorString = board[i][j].getTile().getColor().getCode();
                    image = choseImage(colorString);
                    Pane tmpPane = getPane(gridBoard,j+tmp,i+tmp);
                    tmpPane.getChildren().add(image);
                }
                //gridBoard.add(tmpPane,j+tmp,i+tmp);
            }
        }
    }

    private static Pane getPane(GridPane grid,int j, int i) {
        List<Node> kids = grid.getChildren();
        Pane res = null;
        for(Node n: kids){
            if(GridPane.getColumnIndex(n) == j && GridPane.getRowIndex(n) == i)
                res = (Pane)n;
        }
        return res;
    }

    public static void showCommonAndShelves(){

        // common goals

        MockCommonGoal mockCommonGoal1 = LivingRoom.mockModel.getMockCommonGoal().get(0);
        int numberCGoal1 = mockCommonGoal1.getEnumeration() + 1;
        MockCommonGoal mockCommonGoal2 = LivingRoom.mockModel.getMockCommonGoal().get(1);
        int numberCGoal2 = mockCommonGoal2.getEnumeration() + 1;

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

        // personal goal

        Pane pGoalPane = new Pane();

        ImageView pGoalImg = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        pGoalImg.setPreserveRatio(true);
        pGoalImg.setFitWidth(250);
        pGoalImg.setFitHeight(300);

        pGoalGrid = new GridPane();
        pGoalGrid.setGridLinesVisible(true);
        pGoalGrid.setHgap(10);
        pGoalGrid.setVgap(2);
        pGoalGrid.setLayoutX(30);
        pGoalGrid.setLayoutY(20);
        pGoalGrid.prefWidthProperty().bind(pGoalImg.fitWidthProperty());
        pGoalGrid.prefHeightProperty().bind(pGoalImg.fitWidthProperty());

        pGoalGrid.getChildren().clear();
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                Pane paneBase = new Pane();
                paneBase.setPrefWidth(30);
                paneBase.setPrefHeight(30);
                pGoalGrid.add(paneBase,j,i);
                GridPane.setColumnIndex(paneBase,j);
                GridPane.setRowIndex(paneBase,i);
            }
        }
        pGoalGrid.setOnMouseClicked(event -> {
            try {
                network.selectTiles(localPlayer,selectedTiles);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        pGoalPane.getChildren().addAll(pGoalGrid,pGoalImg);

        // shelves
        HBox hBoxShelves = new HBox();

        int numPlayer = mockModel.getMockPlayers().size();
        for(int i=0;i<numPlayer-1;i++){

            // setting pane and image of shelves
            Pane playerShelfPane = new Pane();

            ImageView shelfImg = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
            shelfImg.setFitWidth(200);
            shelfImg.setFitHeight(250);
            shelfImg.setLayoutX(10);
            shelfImg.setLayoutY(20);
            shelfImg.setPreserveRatio(true);

            // binding the grid pane
            GridPane grid = new GridPane();
            grid.setGridLinesVisible(true);
            grid.setHgap(7);
            grid.setVgap(2);
            grid.setLayoutX(30);
            grid.setLayoutY(30);
            grid.prefWidthProperty().bind(shelfImg.fitWidthProperty());
            grid.prefHeightProperty().bind(shelfImg.fitWidthProperty());
            grid.getChildren().clear();
            for(int k=0;k<6;k++){
                for(int j=0;j<5;j++){
                    Pane paneBase = new Pane();
                    paneBase.setPrefWidth(25);
                    paneBase.setPrefHeight(25);
                    grid.add(paneBase,j,k);
                    GridPane.setColumnIndex(paneBase,j);
                    GridPane.setRowIndex(paneBase,k);
                }
            }
            othersShelves.add(grid);
            playerShelfPane.getChildren().addAll(grid,shelfImg);
            hBoxShelves.getChildren().add(playerShelfPane);
        }


        vBoxShelves.getChildren().add(hBoxShelves);

        hBoxMyShelfAndCG.setSpacing(50);
        hBoxMyShelfAndCG.getChildren().addAll(vBoxCommonGoal,pGoalPane);

        vBoxShelves.setSpacing(20);
        vBoxShelves.getChildren().add(hBoxMyShelfAndCG);
    }
    private static void printSelectedTiles() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Selected Tiles");
        alert.setHeaderText("Selected tiles: ");

        VBox vBoxMain = new VBox();

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

        Button send = new Button("insert");
        send.setOnAction(e-> {
            try {
                insertTiles();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        send.setPrefHeight(40);
        send.setPrefWidth(60);

        HBox input = new HBox();
        input.setPrefWidth(150);
        input.setPrefHeight(20);

        orderTile = new TextField();
        orderTile.setPromptText("Choose order: ");
        orderTile.setPrefWidth(120);
        orderTile.setPrefHeight(20);

        column = new TextField();
        column.setPromptText("Column: ");
        column.setPrefWidth(50);
        column.setPrefHeight(20);

        input.setSpacing(10);
        input.getChildren().addAll(orderTile,column);

        vBoxMain.setSpacing(20);
        vBoxMain.getChildren().addAll(hBoxPopUp,input,send);

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

        String tileToInsert = orderTile.getText();

        String[] pos = tileToInsert.split(",");
        if(pos.length < 1 || pos.length > 3)
            printError("Wrong order!");
        List<Integer> orderToSend = new ArrayList<>();
        for(String p:pos){
            orderToSend.add(Integer.parseInt(p));
        }

        int col = Integer.parseInt(column.getText());

        network.insertTiles(localPlayer,orderToSend,col);
    }
    private static ImageView choseImage(String colorString) {
        ImageView image;
        switch (colorString) {
            case "\u001b[42;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Gatti1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
                return image;
            }
            case "\u001b[47;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Libri1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
                return image;
            }
            case "\u001b[43;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Giochi1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
                return image;
            }
            case "\u001b[44;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Cornici1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
                return image;
            }
            case "\u001b[46;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Trofei1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
                return image;
            }
            case "\u001b[45;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Piante1.1.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
                return image;
            }
            default -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Piante1.2.png")));
                image.setFitHeight(60);
                image.setFitWidth(60);
                image.setPreserveRatio(true);
                return image;
            }
        }
    }

    public static void updateShelves(){

        ImageView image;
        int grids = 0;
        // shelves
        for(int k=0;k<mockModel.getMockPlayers().size();k++){
            if(!localPlayer.equals(mockModel.getMockPlayers().get(k).getPlayerID())) {
                System.out.println("Update other's shelves!");
                Tile[][] othersShelf = mockModel.getMockPlayers().get(k).getShelf();
                GridPane playerGrid = othersShelves.get(grids);
                grids++;
                // delete the old board
                for (int i = 0; i < othersShelf.length; i++) {
                    for (int j = 0; j < othersShelf[0].length; j++) {
                        Pane tmpPane = getPane(playerGrid, j, i);
                        if (tmpPane.getChildren().size() != 0)
                            tmpPane.getChildren().remove(0);
                    }
                }

                // update
                for (int i = 0; i < othersShelf.length; i++) {
                    for (int j = 0; j < othersShelf[0].length; j++) {
                        if (othersShelf[i][j] != null) {
                            String colorString = othersShelf[i][j].getTileColor().getCode();
                            image = choseImage(colorString);
                            image.setPreserveRatio(true);
                            Pane tmpPane = getPane(playerGrid, j, i);
                            image.fitWidthProperty().bind(tmpPane.widthProperty());
                            tmpPane.getChildren().add(image);
                        }
                    }
                }
            }else{
                // personal goal
                Tile[][] personalShelf = mockModel.getPlayer(localPlayer).getShelf();
                // delete the old board
                for(int i=0;i<personalShelf.length;i++) {
                    for (int j = 0; j < personalShelf[0].length; j++) {
                        Pane tmpPane = getPane(pGoalGrid,j,i);
                        if(tmpPane.getChildren().size() != 0)
                            tmpPane.getChildren().remove(0);
                    }
                }
                // adding tiles updated
                for(int i=0;i<personalShelf.length;i++) {
                    for (int j = 0; j < personalShelf[0].length; j++) {
                        if (personalShelf[i][j] != null) {
                            String colorString = personalShelf[i][j].getTileColor().getCode();
                            image = choseImage(colorString);
                            image.setPreserveRatio(true);
                            Pane tmpPane = getPane(pGoalGrid, j, i);
                            image.fitWidthProperty().bind(tmpPane.widthProperty());
                            tmpPane.getChildren().add(image);
                        }
                    }
                }}

        }

    }
    public static void updateBoard(Cell[][] board){
        System.out.println("updating the board");
        int numPlayer = mockModel.getMockPlayers().size();
        ImageView image;
        int tmp = 0;
        if(numPlayer == 2)
            tmp = 1;

        // currentPlayer
        // TODO: 22/06/2023 fix current player

        // delete the old board
        for(int i=0;i<board.length;i++) {
            for (int j = 0; j < board[0].length; j++) {
                Pane tmpPane = getPane(gridBoard,j+tmp,i+tmp);
                if(tmpPane.getChildren().size() != 0)
                    tmpPane.getChildren().remove(0);
            }
        }

        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if (board[i][j].getStatus() && board[i][j].getTile() != null) {
                    String colorString = board[i][j].getTile().getColor().getCode();
                    image = choseImage(colorString);
                    Pane tmpPane = getPane(gridBoard,j+tmp,i+tmp);
                    tmpPane.getChildren().add(image);
                }
            }
        }
    }
}