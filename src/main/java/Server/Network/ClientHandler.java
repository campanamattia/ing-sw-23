package Server.Network;

import Interface.RemoteView;

public abstract class ClientHandler implements RemoteView {
    protected String playerID;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}
