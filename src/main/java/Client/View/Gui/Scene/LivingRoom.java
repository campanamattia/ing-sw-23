package Client.View.Gui.Scene;

import Client.View.Gui.GuiApplication;
import Enumeration.TurnPhase;
import Utils.*;
import Utils.Cell;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static Client.ClientApp.*;

/**
 * The `LivingRoom` class represents the scene that is displayed at the end of the game,
 * showing the final rankings of the players.
 * It extends the JavaFX `Scene` class and provides a UI to display the game state and interacts with the player.
 */
public class LivingRoom extends Scene {
    private static GuiApplication app;
    private ImageView boardImage;
    private static GridPane gridBoard;
    private static GridPane pGoalGrid;
    private static MockModel mockModel;
    private static final HBox hBoxMyShelfAndCG = new HBox();
    private static VBox vBoxShelves;
    private static final HashMap<Coordinates, ImageView> selectedTilesMap = new HashMap<>();
    private static List<ImageView> selectedTilesImg = new ArrayList<>();
    private static final List<GridPane> othersShelves = new ArrayList<>();
    private static final Label systemTextArea = new Label("SYSTEM FIELD");
    private static final VBox chatTextAreaVbox = new VBox();
    private static GridPane highlightBoard;
    private static int numPlayers;
    private static StackPane cg1StackPane;
    private static StackPane cg2StackPane;
    private static int peekCg1;
    private static int peekCg2;
    private static Pane finalPointPain;
    private static Pane boardPane;
    private static final List<Pane> selectTilesPane = new ArrayList<>();
    private static final List<Integer> orderTiles = new ArrayList<>();
    private static final List<Pane> arrowsPaneList = new ArrayList<>();

    /**
     * Class constructor.
     */
    public LivingRoom(GuiApplication app) {

        super(new Pane(), 1400, 768);
        setUserAgentStylesheet(STYLEPATH);

        LivingRoom.app = app;

        String boardImg = String.valueOf(GuiApplication.class.getResource("/img/boards/living-room.png"));
        boardImage = new ImageView(boardImg);
        boardStyle(boardImage);

        boardPane = new Pane();
        gridBoard = new GridPane();
        gridBoard.prefWidthProperty().bind(boardImage.fitWidthProperty());
        gridBoard.prefHeightProperty().bind(boardImage.fitWidthProperty());
        // gridBoard.setGridLinesVisible(true);

        for (int row = 0; row < 9; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(60);
            gridBoard.getRowConstraints().add(rowConstraints);
            gridBoard.addRow(row);
        }
        for (int col = 0; col < 9; col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(60);
            gridBoard.getColumnConstraints().add(colConstraints);
            gridBoard.addColumn(col);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Pane paneBase = new Pane();
                gridBoard.add(paneBase, j, i);
            }
        }

        gridBoard.setLayoutX(41);
        gridBoard.setLayoutY(45);
        gridBoard.setHgap(10);
        gridBoard.setVgap(10);
        gridBoard.setDisable(false);

        highlightBoard = new GridPane();
        highlightBoard.prefWidthProperty().bind(boardImage.fitWidthProperty());
        highlightBoard.prefHeightProperty().bind(boardImage.fitWidthProperty());

        for (int row = 0; row < 9; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(60);
            highlightBoard.getRowConstraints().add(rowConstraints);
            highlightBoard.addRow(row);
        }
        for (int col = 0; col < 9; col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(60);
            highlightBoard.getColumnConstraints().add(colConstraints);
            highlightBoard.addColumn(col);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Rectangle rectangle = new Rectangle(60, 60);
                rectangle.setFill(Color.TRANSPARENT);
                highlightBoard.add(rectangle, j, i);
            }
        }

        highlightBoard.setLayoutX(51);
        highlightBoard.setLayoutY(55);
        highlightBoard.setHgap(10);
        highlightBoard.setVgap(10);
        highlightBoard.setDisable(false);

        StackPane boards = new StackPane();
        boards.prefWidthProperty().bind(boardImage.fitWidthProperty());
        boards.prefHeightProperty().bind(boardImage.fitWidthProperty());
        boards.setLayoutX(41);
        boards.setLayoutY(45);
        boards.setDisable(false);
        boards.getChildren().addAll(gridBoard, highlightBoard);

        boardPane.getChildren().addAll(boardImage, boards);

        vBoxShelves = new VBox();
        vBoxShelves.setSpacing(10);

        highlightBoard.setOnMouseClicked(event -> {
            int tmp = 0;
            if (mockModel.getMockPlayers().size() == 2) tmp = 1;
            double mouseX = event.getX();
            double mouseY = event.getY();
            System.out.println("mouse x: " + mouseX + ", mouse y: " + mouseY);

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

                if (!areSelectable(colIndex, rowIndex))
                    return;

                // rectangle to highlight
                Rectangle toHighlight = getRectangle(highlightBoard, colIndex + tmp, rowIndex + tmp);
                System.out.println("x: " + rowIndex + ", y: " + colIndex);



                Coordinates tile = new Coordinates(rowIndex, colIndex);

                if (selectedTilesMap.containsKey(tile)){
                    toHighlight.setStroke(Color.TRANSPARENT);
                    selectedTilesMap.remove(tile);
                } else {
                    if (getPane(gridBoard, colIndex + tmp, rowIndex + tmp) != null && getPane(gridBoard, colIndex + tmp, rowIndex + tmp).getChildren().size() > 0) {
                        selectedTilesMap.put(tile, (ImageView) getPane(gridBoard, colIndex + tmp, rowIndex + tmp).getChildren().get(0));
                        toHighlight.setStroke(Color.BLACK);
                        toHighlight.setStrokeWidth(2);
                    }
                }
            }
        });


        HBox mainHBox = new HBox();
        mainHBox.setSpacing(10);

        VBox systemInfoAndBoardVBox = new VBox();
        systemInfoAndBoardVBox.setSpacing(10);
        systemInfoAndBoardVBox.getChildren().addAll(boardPane,systemTextArea);

        mainHBox.getChildren().addAll(systemInfoAndBoardVBox, vBoxShelves);

        setRoot(mainHBox);
    }

    private boolean areSelectable(int y, int x) {
        if (x - 1 < 0 || x + 1 >= mockModel.getMockBoard().getBoard().length || y - 1 < 0 || y + 1 >= mockModel.getMockBoard().getBoard().length) {
            return true;
        }
        Cell[][] board = mockModel.getMockBoard().getBoard();
        return Stream.of(board[x - 1][y].getTile(), board[x + 1][y].getTile(), board[x][y - 1].getTile(), board[x][y + 1].getTile()).anyMatch(Objects::isNull);
    }

    private static int column = -1;

    private static Rectangle getRectangle(GridPane grid, int j, int i) {
        List<Node> kids = grid.getChildren();
        Rectangle res = null;
        for (Node n : kids) {
            if (GridPane.getColumnIndex(n) == j && GridPane.getRowIndex(n) == i) res = (Rectangle) n;
        }
        return res;
    }

    /**
     * If you are the first player, this method will redirect you in the Lobby Scene where you can select
     * will join the game.
     */
    public static void toLobbySize() {
        Scene lobbySize = new LobbyScene(app);
        app.switchScene(lobbySize);
    }

    /**
     * Update mockModel which contains all the upgrades.
     *
     * @param mock_model most recent version of a mock model.
     */
    public static void updateMockModel(MockModel mock_model) {
        mockModel = mock_model;
    }

    /**
     * Update common goals and fix the graphics on screen.
     *
     * @param enumeration common goals that are accomplished by a player.
     * @param peek        points to be assigned.
     */
    public static void updateCommonGoal(int enumeration, Integer peek) {
        if (mockModel.getMockCommonGoal().get(0).getEnumeration() == enumeration) {
            if (peek != peekCg1) {
                ObservableList<Node> children = cg1StackPane.getChildren();
                if (!children.isEmpty()) children.remove(children.size() - 1);
                peekCg1 = peek;
            }
        } else {
            if (peek != peekCg2) {
                ObservableList<Node> children = cg2StackPane.getChildren();
                if (!children.isEmpty()) children.remove(children.size() - 1);
                peekCg2 = peek;
            }
        }
    }

    public static void outcomeInsertTiles() {
        selectedTilesImg.clear();
    }


    private void boardStyle(ImageView boardImage) {
        this.boardImage = boardImage;
        boardImage.setPreserveRatio(true);
        boardImage.setFitWidth(690);
        boardImage.setFitHeight(768);
        boardImage.setLayoutX(10);
        boardImage.setLayoutY(10);
        boardImage.setLayoutY(10);
    }

    /**
     * show board at the beginning of the match.
     *
     * @param board Initial board.
     */
    public static void showBoard(Cell[][] board) {
        numPlayers = mockModel.getMockPlayers().size();
        int tmp = 0;
        if (numPlayers == 2) tmp = 1;
        fillBoard(board, tmp);
        finalPointPain = new Pane();
        ImageView finalPointImg = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/scoring_tokens/end_game.jpg")));
        finalPointImg.setPreserveRatio(true);
        finalPointImg.setRotate(10);
        finalPointImg.setFitWidth(60);
        finalPointPain.getChildren().add(finalPointImg);
        finalPointPain.setLayoutX(575);
        finalPointPain.setLayoutY(495);
        boardPane.getChildren().add(finalPointPain);
    }

    private static Pane getPane(GridPane grid, int j, int i) {
        List<Node> kids = grid.getChildren();
        Pane res = null;
        for (Node n : kids) {
            if (GridPane.getColumnIndex(n) == j && GridPane.getRowIndex(n) == i) res = (Pane) n;
        }
        return res;
    }

    /**
     * Show common goals anc shelves at the beginning of the game.
     */
    public static void showCommonAndShelves() {

        // common goals
        MockCommonGoal mockCommonGoal1 = mockModel.getMockCommonGoal().get(0);
        int numberCGoal1 = mockCommonGoal1.getEnumeration() + 1;
        String commonGoal1Description = mockCommonGoal1.getDescription();
        MockCommonGoal mockCommonGoal2 = mockModel.getMockCommonGoal().get(1);
        int numberCGoal2 = mockCommonGoal2.getEnumeration() + 1;
        String commonGoal2Description = mockCommonGoal2.getDescription();

        Pane cg1 = new Pane();
        Pane cg2 = new Pane();
        ImageView cg1img = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/common_goal_cards/" + numberCGoal1 + ".jpg")));
        ImageView cg2img = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/common_goal_cards/" + numberCGoal2 + ".jpg")));
        cg1img.setFitWidth(150);
        cg1img.setPreserveRatio(true);
        cg1img.setLayoutX(30);
        cg1img.setLayoutY(15);
        cg2img.setFitWidth(150);
        cg2img.setPreserveRatio(true);
        cg2img.setLayoutX(30);
        cg2img.setLayoutY(15);
        copyImageGenerator(cg1, cg1img, commonGoal1Description);
        copyImageGenerator(cg2, cg2img, commonGoal2Description);

        VBox commonGoal = new VBox();
        commonGoal.setLayoutY(30);
        commonGoal.getChildren().addAll(cg1, cg2);

        // images of common goals points
        // list 2-4-6-8
        // index 0-1-2-3
        List<ImageView> commonGoalsImg = new ArrayList<>();
        for (int i = 2; i <= 8; i += 2) {
            System.out.println("adding image to list");
            commonGoalsImg.add(new ImageView(String.valueOf(GuiApplication.class.getResource("/img/scoring_tokens/scoring_" + i + ".jpg"))));
        }
        switch (numPlayers) {
            case 2 -> {
                commonGoalsImg.remove(0);
                commonGoalsImg.remove(1);
            }
            case 3 -> commonGoalsImg.remove(0);
        }

        peekCg1 = 8;
        peekCg2 = 8;

        // positioning
        // stackPane -> pane -> img

        cg1StackPane = createStackPaneWithImages(commonGoalsImg);
        cg1.getChildren().add(cg1StackPane);

        cg2StackPane = createStackPaneWithImages(commonGoalsImg);
        cg2.getChildren().add(cg2StackPane);

        // personal goal

        Label personalID = new Label("Your shelf");
        personalID.setStyle("-fx-font-weight: bold;");
        personalID.getStyleClass().add("personal-shelf-label");

        Pane pGoalPane = new Pane();

        ImageView pGoalImg = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        pGoalImg.setPreserveRatio(true);
        pGoalImg.setFitWidth(250);
        pGoalImg.setFitHeight(300);

        pGoalGrid = new GridPane();
        personalGoalGrid(pGoalImg, pGoalGrid);

        ImageView image;

        GridPane wallpaper = new GridPane();
        personalGoalGrid(pGoalImg, wallpaper);
        Tile[][] personalGoal = mockModel.getPlayer(localPlayer).getPersonalGoal();
        for (int i = 0; i < personalGoal.length; i++) {
            for (int j = 0; j < personalGoal[0].length; j++) {
                if (personalGoal[i][j] != null) {
                    String colorString = personalGoal[i][j].color().getCode();
                    image = choseImage(colorString);
                    image.setPreserveRatio(true);
                    image.setOpacity(0.5);
                    Pane tmpPane = getPane(wallpaper, j, i);
                    image.fitWidthProperty().bind(tmpPane.widthProperty());
                    tmpPane.getChildren().add(image);
                }
            }
        }

        pGoalGrid.setOnMouseClicked(event -> {
            if (mockModel.getTurnPhase() == TurnPhase.PICKING) {
                if (selectedTilesMap.size() == 0)
                    return;
                List<Coordinates> selectedTiles = new ArrayList<>(selectedTilesMap.keySet());
                System.out.println(selectedTiles);
                System.out.println("selected tiles: " + selectedTiles);
                try {
                    network.selectTiles(localPlayer, selectedTiles);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else if (mockModel.getTurnPhase() == TurnPhase.INSERTING) {
                printSelectedTiles();
            }
        });

        pGoalPane.getChildren().addAll(wallpaper, pGoalGrid, pGoalImg, personalID);

        // shelves

        HBox hBoxShelves = new HBox();

        for (int i = 0; i < numPlayers; i++) {

            // setting pane and image of shelves
            Pane playerShelfPane = new Pane();

            if (!localPlayer.equals(mockModel.getMockPlayers().get(i).getPlayerID())) {
                Label playerID = new Label(mockModel.getMockPlayers().get(i).getPlayerID() + "'s shelf.");
                playerID.getStyleClass().add("players-shelf-label");
                playerShelfPane.getChildren().add(playerID);


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
                for (int k = 0; k < 6; k++) {
                    for (int j = 0; j < 5; j++) {
                        Pane paneBase = new Pane();
                        paneBase.setPrefWidth(25);
                        paneBase.setPrefHeight(25);
                        grid.add(paneBase, j, k);
                        GridPane.setColumnIndex(paneBase, j);
                        GridPane.setRowIndex(paneBase, k);
                    }
                }
                othersShelves.add(grid);
                playerShelfPane.getChildren().addAll(grid, shelfImg);
                hBoxShelves.getChildren().add(playerShelfPane);
            }
        }


        vBoxShelves.getChildren().add(hBoxShelves);

        hBoxMyShelfAndCG.setSpacing(20);
        hBoxMyShelfAndCG.getChildren().addAll(pGoalPane, commonGoal);



        //System.out.println("adding common and personal shelf");
        vBoxShelves.getChildren().add(hBoxMyShelfAndCG);

        // chat

        VBox chatLayout = new VBox();
        chatLayout.setSpacing(10);

        ScrollPane chatTextArea = new ScrollPane();
        chatTextArea.getStyleClass().add("chat-area");
        chatTextArea.setContent(chatTextAreaVbox);

        chatTextAreaVbox.getStyleClass().add("vBox-chat");

        TextField messageField = new TextField();
        messageField.getStyleClass().add("text-field-chat");

        Button sendButton = new Button("Send");
        sendButton.getStyleClass().add("button-chat");

        HBox hBoxInputMessage = new HBox();
        hBoxInputMessage.setSpacing(10);
        hBoxInputMessage.setPrefWidth(600);
        hBoxInputMessage.setPrefHeight(25);

        hBoxInputMessage.getChildren().addAll(messageField, sendButton);

        chatLayout.getChildren().addAll(chatTextArea, hBoxInputMessage);
        vBoxShelves.getChildren().add(chatLayout);

        sendButton.setOnAction(event -> sendMessage(messageField));

        messageField.setOnAction(event -> sendMessage(messageField));

        // upgrading shelves in case of reconnection
        updateShelves();
    }

    private static void sendMessage(TextField messageField) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CHAT");
        alert.setHeaderText("MESSAGE TO: ");

        VBox dest = new VBox();
        dest.setSpacing(10);

        for (int i = 0; i < mockModel.getMockPlayers().size(); i++) {
            Button playerButton;
            if (!localPlayer.equals(mockModel.getMockPlayers().get(i).getPlayerID())) {
                playerButton = new Button(mockModel.getMockPlayers().get(i).getPlayerID());
                playerButton.getStyleClass().add("chat-button");
                playerButton.setId(mockModel.getMockPlayers().get(i).getPlayerID());
            }
            else{
                playerButton = new Button("ALL");
                playerButton.getStyleClass().add("chat-button");
                playerButton.setId("ALL");
            }
            playerButton.setOnMouseClicked(e->{
                handleClickChat(messageField, playerButton.getId());
                alert.close();
            });
            dest.getChildren().add(playerButton);
        }

        alert.getDialogPane().setContent(dest);

        alert.showAndWait();
    }

    private static void handleClickChat(TextField messageField, String dest) {
        String message = messageField.getText();

        if (Objects.equals(dest, "ALL")) dest = null;
        try {
            network.writeChat(localPlayer, message, dest);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        messageField.clear();
    }

    private static void personalGoalGrid(ImageView pGoalImg, GridPane pGoalGrid) {
        pGoalGrid.setGridLinesVisible(true);
        pGoalGrid.setHgap(10);
        pGoalGrid.setVgap(2);
        pGoalGrid.setLayoutX(30);
        pGoalGrid.setLayoutY(20);
        pGoalGrid.prefWidthProperty().bind(pGoalImg.fitWidthProperty());
        pGoalGrid.prefHeightProperty().bind(pGoalImg.fitWidthProperty());

        pGoalGrid.getChildren().clear();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Pane paneBase = new Pane();
                paneBase.setPrefWidth(30);
                paneBase.setPrefHeight(30);
                pGoalGrid.add(paneBase, j, i);
                GridPane.setColumnIndex(paneBase, j);
                GridPane.setRowIndex(paneBase, i);
            }
        }
    }

    private static StackPane createStackPaneWithImages(List<ImageView> images) {
        StackPane stackPane = new StackPane();
        for (ImageView image : images) {
            ImageView imageView = new ImageView(image.getImage());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(40);
            imageView.setRotate(350);
            stackPane.getChildren().add(imageView);
        }
        stackPane.setLayoutX(120);
        stackPane.setLayoutY(40);
        return stackPane;
    }

    private static void copyImageGenerator(Pane cg1, ImageView cg1img, String commonGoalXDescription) {
        Image copyCg1Image = cg1img.getImage();
        ImageView copyCg1 = new ImageView(copyCg1Image);
        copyCg1.setPreserveRatio(true);
        copyCg1.setFitWidth(250);
        cg1.getChildren().add(cg1img);
        cg1.setOnMouseClicked(e -> showCommonGoals(copyCg1, commonGoalXDescription, e.getScreenX(), e.getScreenY()));
    }

    private static void printSelectedTiles() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Selected Tiles");
        alert.setHeaderText("Selected tiles: ");
        alert.getButtonTypes().clear();

        // vBox -> hBox -> shelf

        VBox vBoxMain = new VBox();
        vBoxMain.setSpacing(10);

        HBox selectedTilesHBox = new HBox();
        selectedTilesHBox.setSpacing(10);
        for (int i = 0; i < selectedTilesImg.size(); i++) {
            Pane tilePane = new Pane();
            tilePane.setId(String.valueOf(i + 1));
            selectTilesPane.add(tilePane);
            tilePane.setOnMouseClicked(e -> handleClickTileOrder(tilePane.getId()));

            ImageView tileImg = selectedTilesImg.get(i);
            tileImg.setFitWidth(60);
            tileImg.setFitHeight(60);

            tilePane.getChildren().add(tileImg);
            selectedTilesHBox.getChildren().add(tilePane);
        }
        for (Pane pane : selectTilesPane) {
            printNumber(pane, pane.getId());
        }
        Pane personalGoalPane = new Pane();

        ImageView personalGoalImg = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/boards/bookshelf.png")));
        personalGoalImg.setPreserveRatio(true);
        personalGoalImg.setFitWidth(250);
        personalGoalImg.setFitHeight(300);

        GridPane personalGoalGrid = new GridPane();
        personalGoalGrid(personalGoalImg, personalGoalGrid);
        updatePersonalGoal(personalGoalGrid);

        Button insert = new Button("insert");
        insert.setOnAction(e -> {
            try {
                insertTiles();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            } finally {
                alert.close();
            }
        });
        insert.setPrefHeight(40);
        insert.setPrefWidth(60);

        personalGoalPane.getChildren().addAll(personalGoalGrid, personalGoalImg);

        HBox hBoxArrows = new HBox();
        hBoxArrows.setSpacing(10);
        for (int i = 0; i < 5; i++) {
            Pane arrowPane = new Pane();
            arrowPane.setId(String.valueOf(i));

            ImageView arrowImg = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/misc/down-filled-triangular-arrow.png")));
            arrowImg.setPreserveRatio(true);
            arrowImg.setFitWidth(25);

            arrowPane.setOnMouseClicked(e -> chooseColumn(arrowPane, Integer.parseInt(arrowPane.getId())));

            arrowPane.getChildren().add(arrowImg);
            arrowsPaneList.add(arrowPane);
            hBoxArrows.getChildren().add(arrowPane);
        }
        Pane firstPane = (Pane) hBoxArrows.getChildren().get(0);
        HBox.setMargin(firstPane, new Insets(0, 0, 0, 40));

        vBoxMain.getChildren().addAll(selectedTilesHBox, hBoxArrows, personalGoalPane, insert);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().add(okButtonType);

        Button okButton = (Button) alert.getDialogPane().lookupButton(okButtonType);
        okButton.setText("VIEW BOARD");

        alert.getDialogPane().setContent(vBoxMain);

        alert.showAndWait();
    }

    private static void chooseColumn(Pane arrowPane, int col) {
        for (Pane pane : arrowsPaneList) {
            ImageView img = (ImageView) pane.getChildren().get(0);
            img.setOpacity(1);
        }
        ImageView imagePane = new ImageView();
        if (arrowPane.getChildren().size() > 0) imagePane = (ImageView) arrowPane.getChildren().get(0);
        if (col != column) {
            column = col;
            imagePane.setOpacity(0.5);
        } else {
            column = -1;
        }
    }

    private synchronized static void printNumber(Pane tilePane, String id) {
        if (tilePane.getChildren().size() > 1) tilePane.getChildren().remove(1);

        if (!orderTiles.contains(Integer.parseInt(id))) {
            return;
        }

        int currentPos = orderTiles.indexOf(Integer.parseInt(id)) + 1;
        String currPosStr = String.valueOf(currentPos);
        Label toShow = new Label(currPosStr);
        toShow.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px");
        toShow.setLayoutX(25);
        toShow.setLayoutY(25);
        tilePane.getChildren().add(toShow);
    }

    private synchronized static void handleClickTileOrder(String id) {
        if (!orderTiles.contains(Integer.parseInt(id))) {
            orderTiles.add(Integer.parseInt(id));
        } else {
            orderTiles.remove(Integer.valueOf(Integer.parseInt(id)));
        }
        for (Pane pane : selectTilesPane)
            printNumber(pane, pane.getId());
    }

    /**
     *
     */
    public static void outcomeSelectTiles() {
        clearBoard();
        selectedTilesImg = new ArrayList<>(selectedTilesMap.values());
        printSelectedTiles();
    }

    private static void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Rectangle rt = getRectangle(highlightBoard, j, i);
                rt.setStroke(Color.TRANSPARENT);
            }
        }
    }

    /**
     * Insert the tile in the shelf.
     */
    public static void insertTiles() throws RemoteException {
        System.out.println("insert tiles: " + orderTiles + " in column: " + column);
        List<Integer> clone = new ArrayList<>(orderTiles);
        network.insertTiles(localPlayer, clone, column);
        orderTiles.clear();
    }

    private static ImageView choseImage(String colorString) {
        ImageView image;
        switch (colorString) {
            case "\u001b[42;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Cats1.png")));
                return cssTile(image);
            }
            case "\u001b[47;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Books1.png")));
                return cssTile(image);
            }
            case "\u001b[43;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Toys1.png")));
                return cssTile(image);
            }
            case "\u001b[44;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Frames1.png")));
                return cssTile(image);
            }
            case "\u001b[46;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Trophies1.png")));
                return cssTile(image);
            }
            case "\u001b[45;1m" -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Plants1.png")));
                return cssTile(image);
            }
            default -> {
                image = new ImageView(String.valueOf(GuiApplication.class.getResource("/img/item_tiles/Plants2.png")));
                return cssTile(image);
            }
        }
    }

    private static ImageView cssTile(ImageView image) {
        image.setFitHeight(60);
        image.setFitWidth(60);
        image.setPreserveRatio(true);
        return image;
    }

    /**
     *
     */
    public static void updateShelves() {
        int grids = 0;
        // shelves
        for (int k = 0; k < mockModel.getMockPlayers().size(); k++) {
            if (!localPlayer.equals(mockModel.getMockPlayers().get(k).getPlayerID())) {
                //System.out.println("Update other's shelves!");
                Tile[][] othersShelf = mockModel.getMockPlayers().get(k).getShelf();
                GridPane playerGrid = othersShelves.get(grids);
                grids++;
                updateShelvesOrPersonalGoal(othersShelf, playerGrid);
            } else {
                // personal goal
                updatePersonalGoal(pGoalGrid);
            }
        }
    }

    private static void updateShelvesOrPersonalGoal(Tile[][] Shelf, GridPane grid) {
        ImageView image;
        // delete the old board
        for (int i = 0; i < Shelf.length; i++) {
            for (int j = 0; j < Shelf[0].length; j++) {
                Pane tmpPane = getPane(grid, j, i);
                if (tmpPane.getChildren().size() != 0) tmpPane.getChildren().remove(0);
            }
        }

        // update
        for (int i = 0; i < Shelf.length; i++) {
            for (int j = 0; j < Shelf[0].length; j++) {
                if (Shelf[i][j] != null) {
                    String colorString = Shelf[i][j].color().getCode();
                    image = choseImage(colorString);
                    image.setPreserveRatio(true);
                    Pane tmpPane = getPane(grid, j, i);
                    image.fitWidthProperty().bind(tmpPane.widthProperty());
                    tmpPane.getChildren().add(image);
                }
            }
        }
    }

    private static void updatePersonalGoal(GridPane grid) {
        Tile[][] personalShelf = mockModel.getPlayer(localPlayer).getShelf();
        // delete the old board
        updateShelvesOrPersonalGoal(personalShelf, grid);
    }

    /**
     * Update the board with the update received by the server.
     *
     * @param board most recent update of the board.
     */
    public static void updateBoard(Cell[][] board) {
        //System.out.println("updating the board");
        int tmp = 0;
        if (numPlayers == 2) tmp = 1;

        // delete the old board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Pane tmpPane = getPane(gridBoard, j + tmp, i + tmp);
                if (tmpPane.getChildren().size() != 0) tmpPane.getChildren().remove(0);
            }
        }

        fillBoard(board, tmp);
    }

    private static void fillBoard(Cell[][] board, int tmp) {
        ImageView image;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getStatus() && board[i][j].getTile() != null) {
                    String colorString = board[i][j].getTile().color().getCode();
                    image = choseImage(colorString);
                    Pane tmpPane = getPane(gridBoard, j + tmp, i + tmp);
                    tmpPane.getChildren().add(image);
                }
            }
        }
    }

    /**
     * Switch a scene to End Game Scene.
     *
     * @param leaderboard rank of the player.
     */
    public static void toEndGame(List<Rank> leaderboard) {
        if (leaderboard == null){
            System.out.println("leaderboard null");
            return;
        }
        EndGameScene endGameScene = new EndGameScene(app);
        app.switchScene(endGameScene);
    }

    private static void showCommonGoals(ImageView commonGoalImg, String commonGoalXDescription, double mouseX, double mouseY) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("COMMON GOALS");
        alert.setHeaderText("COMMON GOALS: ");

        VBox toShow = new VBox();
        toShow.setSpacing(20);

        commonGoalImg.setFitWidth(250);
        commonGoalImg.setPreserveRatio(true);

        TextFlow textFlow = new TextFlow();

        Text commonGoalDescription = new Text(commonGoalXDescription);
        commonGoalDescription.wrappingWidthProperty().bind(alert.widthProperty());
        Font customFont = Font.loadFont(String.valueOf(GuiApplication.class.getResource("/font/Poppins-Regular.ttf")),15);
        commonGoalDescription.setFont(customFont);
        textFlow.getChildren().add(commonGoalDescription);

        toShow.getChildren().addAll(commonGoalImg, textFlow);

        alert.getDialogPane().setContent(toShow);
        alert.getDialogPane().setPrefWidth(400);
        alert.getDialogPane().setPrefHeight(400);

        alert.setX(mouseX - 300.00);
        alert.setY(mouseY);

        alert.showAndWait();
    }

    /**
     * Show in chat the new message.
     *
     * @param message message to show in chat.
     */
    public static void newMessageChat(ChatMessage message) {
        String from = "From " + message.from();
        String dest = " to " + message.to() + " : ";
        String toShow = from + dest + "'" + message.message() + "'";
        if (message.to() == null) {
            from += ": ";
            dest = "";
            toShow = from + dest + "'" + message.message() + "'";
        }
        Label tmp = new Label(toShow);
        chatTextAreaVbox.getChildren().add(tmp);
    }

    public static void setUp(){
        clearBoard();
        selectedTilesMap.clear();
        selectedTilesImg.clear();
    }

    /**
     * Show in chat the information.
     *
     * @param info message to print.
     */
    public static void writeInfos(String info) {
        systemTextArea.setText("");
        systemTextArea.getStyleClass().add("text-info-chat");
        systemTextArea.setText(info);
    }

    /**
     * Show in chat the warning.
     *
     * @param message warning to show in chat.
     */
    public static void outcomeMessage(String message) {
        systemTextArea.setText("");
        systemTextArea.getStyleClass().add("text-info-chat");
        systemTextArea.setTextFill(Color.GREEN);
        systemTextArea.setText(message);
    }

    /**
     * Reset of the chat for a player that has been disconnected.
     *
     * @param messageList list of old messages to show in chat.
     */
    public static void refreshChat(List<ChatMessage> messageList) {
        for (ChatMessage message : messageList) {
            LivingRoom.newMessageChat(message);
        }
    }

    public static void lastRound() {
        if (finalPointPain.getChildren().size() == 0) return;
        finalPointPain.getChildren().clear();
    }
}