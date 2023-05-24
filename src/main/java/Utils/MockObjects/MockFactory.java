package Utils.MockObjects;

import Server.Controller.GameController;
import Server.Model.GameModel;
import Server.Model.LivingRoom.Board;
import Server.Model.LivingRoom.CommonGoal.CommonGoal;
import Server.Model.Player.Player;
import Utils.Cell;
import Utils.Coordinates;

public class MockFactory {
    public static MockCommonGoal getMock(CommonGoal commonGoal) { // TODO: 16/05/2023 mockcommongoal

        return null;
    }

    public static MockBoard getMock(Board board) {
        Cell[][] mockBoard = board.getBoard();
        MockBoard mock = new MockBoard();
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

    public static MockModel getMock(GameModel model) {
        MockModel mock = new MockModel();
        mock.setMockBoard(getMock(model.getBoard()));
        for(CommonGoal commonGoal : model.getCommonGoals()) {
            mock.addMockCommonGoal(getMock(commonGoal));
        }
        return mock;
    }
}
