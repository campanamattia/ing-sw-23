package Server.Network;

public abstract class ClientHandler {
    protected String playerID;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}
