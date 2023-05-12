package Server.Network.Lobby;

import java.util.HashMap;

public record Game(String name, HashMap<String, Boolean> players) {

    public void setStatus(String playerID, boolean status) {
        this.players.put(playerID, status);
    }
}
