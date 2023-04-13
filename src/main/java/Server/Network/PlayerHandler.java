package Server.Network;

public abstract class PlayerHandler implements Runnable {
    protected String playerID;

    @Override
    public void run() {

    }

    public String getPlayerID() {
        return playerID;
    }
}