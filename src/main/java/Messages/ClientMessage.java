package Messages;

import Enumeration.OperationType;


public abstract class ClientMessage {
    protected OperationType operationType;
    protected String content;
    protected String playerID;


    public OperationType getOperationType() {
        return operationType;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
