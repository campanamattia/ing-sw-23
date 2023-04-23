package Messages;

import Enumeration.OperationType;

public abstract class ClientMessage {
    protected OperationType operationType;
    protected String playerID;


    public OperationType getOperationType() {
        return operationType;
    }

    public String getPlayerID() {
        return playerID;
    }
}
