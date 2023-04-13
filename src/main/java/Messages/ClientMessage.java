package Messages;

import Enumeration.OpType;

public abstract class ClientMessage {
    protected OpType code;
    protected String playerID;


    public OpType getCode() {
        return code;
    }

    public String getPlayerID() {
        return playerID;
    }
}
