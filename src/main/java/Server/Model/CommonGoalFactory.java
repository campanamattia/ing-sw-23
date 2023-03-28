package Server.Model;
import Server.Model.CommonGoalPackage.*;
import com.google.gson.JsonObject;

import java.util.List;

public class CommonGoalFactory {
    public static CommonGoal getCommonGoal(List<Integer> tokenList, JsonObject jsonObject) {

        switch (jsonObject.get("enum").getAsInt()) {

            case 0 -> {
                return new GroupAdjacentGoal(tokenList, jsonObject);
            }
            case 1 -> {
                return new VerticesGoal(tokenList, jsonObject);
            }
            case 2 -> {
                return new DoubleSquareGoal(tokenList, jsonObject);
            }
            case 3 -> {
                return new SameNGoal(tokenList, jsonObject);
            }
            case 4 -> {
                return new DiagonalGoal(tokenList, jsonObject);
            }
            case 5 -> {
                return new CrossGoal(tokenList, jsonObject);
            }
            case 6 -> {
                return new StaircaseGoal(tokenList, jsonObject);
            }
            case 7 -> {
                return new RowColumnGoal(tokenList, jsonObject);
            }
        }
        return null;
    }
}
