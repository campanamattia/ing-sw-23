package Server.Model.LivingRoom.CommonGoal;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
class CommonGoalFactoryTest {

    private static JsonArray array;
    private static List<Integer> scoringToken;

    @BeforeAll
    static void setUp() {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("settings/commonGoal.json"))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        array = json.get("commonGoal").getAsJsonArray();
        scoringToken = Arrays.asList(2, 4, 6, 8);
    }

    @Test
    void createAllCommonGoal(){
        int i;
        for(i = 0; i < array.size(); i++){
            if (CommonGoalFactory.getCommonGoal(scoringToken, array.get(i).getAsJsonObject()) instanceof CommonGoal)
                continue;
            else
                fail();
        }
        int finalI = i;
        assertThrows(IndexOutOfBoundsException.class, () -> CommonGoalFactory.getCommonGoal(scoringToken, array.get(finalI).getAsJsonObject()));
    }
}