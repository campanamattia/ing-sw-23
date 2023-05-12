package Server.Network.Lobby;

import java.util.HashMap;

public class Game {
    private final String name;
    private final HashMap<String, Boolean> players;

    public Game(String name, HashMap<String, Boolean> playerID) {
        this.name = name;
        this.players = playerID;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<String, Boolean> getPlayers() {
        return this.players;
    }
    public void setStatus(String playerID, boolean status) {
        this.players.put(playerID, status);
    }
}
