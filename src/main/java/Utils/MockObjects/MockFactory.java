package Utils.MockObjects;

import Server.Model.GameModel;
import Server.Model.LivingRoom.Board;
import Server.Model.LivingRoom.CommonGoal.CommonGoal;
import Server.Model.Player.Player;
import Utils.Cell;
import Utils.ChatMessage;

import java.util.Stack;

/**
 * It is used to create mock objects of the model, the board, the players and the common goals.
 * It has a static method for each object to be mocked.
 */
public class MockFactory {

    /**
     * It creates a mock object of the common goal.
     * @param commonGoal the common goal to be mocked
     * @return the mock common goal object
     */
    public static MockCommonGoal getMock(CommonGoal commonGoal) {
        MockCommonGoal mock = new MockCommonGoal();
        mock.setEnumeration(commonGoal.getEnumeration());
        mock.setDescription(commonGoal.getDescription());
        mock.setScoringToken((Stack<Integer>) commonGoal.getScoringToken().clone());
        return mock;
    }

    /**
     * It creates a mock object of the board.
     * @param board the board to be mocked
     * @return the mock board object
     */
    public static MockBoard getMock(Board board) {
        MockBoard mock = new MockBoard();
        Cell[][] mockBoard = board.getBoard();
        mock.setBoard(mockBoard.clone());
        return mock;
    }

    /**
     * It creates a mock object of the player.
     * @param player the player to be mocked
     * @return the mock player object
     */
    public static MockPlayer getMock(Player player) {
        MockPlayer mock = new MockPlayer();
        mock.setPlayerID(player.getPlayerID());
        mock.setPersonalGoal(player.getPersonalGoal().getPersonalGoal().clone());
        mock.setShelf(player.getMyShelf().getMyShelf().clone());
        mock.setScore(player.getSharedScore());
        return mock;
    }

    /**
     * It creates a mock object of the model.
     * It uses the other methods of this class to mock the board, the players and the common goals.
     * @param model the model to be mocked
     * @return the mock model object
     */
    public static MockModel getMock(GameModel model) {
        MockModel mock = new MockModel();
        mock.setMockBoard(getMock(model.getBoard()));
        for (CommonGoal commonGoal : model.getCommonGoals()) {
            mock.addMockCommonGoal(getMock(commonGoal));
        }
        for (Player player : model.getPlayers()) {
            mock.addMockPlayer(getMock(player));
        }
        mock.setChat((Stack<ChatMessage>) model.getChatRoom().getFlow().clone());
        mock.setCurrentPlayer(model.getCurrentPlayer().getPlayerID());
        return mock;
    }
}
