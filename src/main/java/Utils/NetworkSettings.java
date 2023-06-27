package Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.util.Objects;

public class NetworkSettings {
    /**
     * The file path for the server setting JSON file.
     */
    private static final String serverSetting = "settings/serverSetting.json";

    public static String ipHostFromJSON() {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(serverSetting))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get("ipHost").getAsString();
    }

    @SuppressWarnings("ConstantConditions")
    public static int rmiFromJSON() throws RuntimeException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(serverSetting))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get("rmiPort").getAsInt();
    }

    @SuppressWarnings("ConstantConditions")
    public static int socketFromJSON() throws RuntimeException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(serverSetting))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get("socketPort").getAsInt();
    }
}
