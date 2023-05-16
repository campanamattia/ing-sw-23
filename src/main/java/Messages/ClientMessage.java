package Messages;

import Enumeration.OperationType;
import Server.Network.Client.SocketHandler;

public abstract class ClientMessage {
    protected String playerID;

    public ClientMessage(){
        this.playerID = null;
    }

    public String getPlayerID() {
        return playerID;
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public abstract void execute(SocketHandler socket);
}
