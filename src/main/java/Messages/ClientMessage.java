package Messages;

import Server.Network.Client.SocketHandler;

public abstract class ClientMessage {
    protected String playerID;

    public ClientMessage(){
        this.playerID = null;
    }

    public abstract void execute(SocketHandler socket);
}
