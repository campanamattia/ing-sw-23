package Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.util.Objects;

/**
 * The NetworkSettings class provides utility methods to retrieve network settings from a JSON file.
 */
public class NetworkSettings {

    /**
     * The file path for the server setting JSON file.
     */
    private static final String serverSetting = "settings/serverSetting.json";

    /**
     * Retrieves the IP host from the JSON file.
     *
     * @return the IP host as a string
     */
    public static String ipHostFromJSON() {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(serverSetting))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get("ipHost").getAsString();
    }

    /**
     * Retrieves the RMI port from the JSON file.
     *
     * @return the RMI port as an integer
     * @throws RuntimeException if the port retrieval fails
     */
    @SuppressWarnings("ConstantConditions")
    public static int rmiFromJSON() throws RuntimeException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(serverSetting))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get("rmiPort").getAsInt();
    }

    /**
     * Retrieves the socket port from the JSON file.
     *
     * @return the socket port as an integer
     * @throws RuntimeException if the port retrieval fails
     */
    @SuppressWarnings("ConstantConditions")
    public static int socketFromJSON() throws RuntimeException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(serverSetting))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get("socketPort").getAsInt();
    }
}
