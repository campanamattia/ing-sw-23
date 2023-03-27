package Server.Model;
import Model.CommonGoalPackage.*;
import Server.Model.CommonGoalPackage.*;

public class CommonGoalFactory {
    public CommonGoal getCommonGoal(int r,int nPlayer) {
        assert r >=0;
        assert r<=11;

        switch (r) {
            case 0 -> {
                return new GroupTwoGoal(nPlayer);
            }
            case 1 -> {
                return new VerticesGoal(nPlayer);
            }
            case 2 -> {
                return new GroupFourGoal(nPlayer);
            }
            case 3 -> {
                return new DoubleSquareGoal(nPlayer);
            }
            case 4 -> {
                return new ThreeColumnGoal(nPlayer);
            }
            case 5 -> {
                return new SameEightGoal(nPlayer);
            }
            case 6 -> {
                return new DiagonalGoal(nPlayer);
            }
            case 7 -> {
                return new FourRowGoal(nPlayer);
            }
            case 8 -> {
                return new DifferentColumnGoal(nPlayer);
            }
            case 9 -> {
                return new DifferentRowGoal(nPlayer);
            }
            case 10 -> {
                return new CrossGoal(nPlayer);
            }
            case 11 -> {
                return new StaircaseGoal(nPlayer);
            }
        }
        return null;
    }
}
