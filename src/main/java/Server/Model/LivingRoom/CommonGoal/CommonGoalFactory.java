package Server.Model.LivingRoom.CommonGoal;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * This class is a factory for common goals.
 * It is used to create a common goal from a json object.
 */
public class CommonGoalFactory {
    /**
     * This method creates a common goal from a json object.
     * @param tokenList the list of scoring tokens.
     * @param jsonObject the json object containing the common goal.
     * @return the common goal created.
     */
    public static CommonGoal getCommonGoal(List<Integer> tokenList, JsonObject jsonObject) {

        switch (jsonObject.get("enum").getAsInt()) {

            case 0, 1 -> {
                return new GroupAdjacentGoal(tokenList, jsonObject);
            }
            case 2 -> {
                return new VerticesGoal(tokenList, jsonObject);
            }
            case 3 -> {
                return new SquareGoal(tokenList, jsonObject);
            }
            case 4 -> {
                return new SameNGoal(tokenList, jsonObject);
            }
            case 5 -> {
                return new DiagonalGoal(tokenList, jsonObject);
            }
            case 6 -> {
                return new CrossGoal(tokenList, jsonObject);
            }
            case 7 -> {
                return new StaircaseGoal(tokenList, jsonObject);
            }
            case 8, 9, 10, 11 -> {
                return new RowColumnGoal(tokenList, jsonObject);
            }
            default -> throw new IllegalStateException("Unexpected value: " + jsonObject.get("enum").getAsInt());
        }
    }
}
