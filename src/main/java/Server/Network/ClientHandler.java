package Server.Network;

import Interface.View;

public abstract class ClientHandler implements View {
    protected String playerID;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}
