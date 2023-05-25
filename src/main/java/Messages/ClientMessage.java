package Messages;

import Server.Network.Client.SocketHandler;

import java.io.Serializable;

public abstract class ClientMessage implements Serializable {
    protected String playerID;

    public abstract void execute(SocketHandler socket);
}
