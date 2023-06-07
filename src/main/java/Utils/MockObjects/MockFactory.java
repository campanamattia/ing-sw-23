package Utils.MockObjects;

import Server.Model.GameModel;
import Server.Model.LivingRoom.Board;
import Server.Model.LivingRoom.CommonGoal.CommonGoal;
import Server.Model.Player.Player;
import Utils.Cell;
import Utils.ChatMessage;

import java.util.Stack;

public class MockFactory {

    @SuppressWarnings("unchecked")
    public static MockCommonGoal getMock(CommonGoal commonGoal) {
        MockCommonGoal mock = new MockCommonGoal();
        mock.setEnumeration(commonGoal.getEnumeration());
        mock.setDescription(commonGoal.getDescription());
        mock.setScoringToken((Stack<Integer>) commonGoal.getScoringToken().clone());
        return mock;
    }

    public static MockBoard getMock(Board board) {
        MockBoard mock = new MockBoard();
        Cell[][] mockBoard = board.getBoard();
        mock.setBoard(mockBoard.clone());
        return mock;
    }

    public static MockPlayer getMock(Player player) {
        MockPlayer mock = new MockPlayer();
        mock.setPlayerID(player.getPlayerID());
        mock.setPersonalGoal(player.getPersonalGoal().getPersonalGoal().clone());
        mock.setShelf(player.getMyShelf().getMyShelf().clone());
        mock.setScore(player.getScore());
        return mock;
    }

    @SuppressWarnings("unchecked")
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
