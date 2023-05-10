package Messages;

import Enumeration.OperationType;

public abstract class ClientMessage {
    protected OperationType operationType;
    protected String playerID;

    public ClientMessage(){
        this.operationType = null;
        this.playerID = null;
    }

    public OperationType getOperationType() {
        return operationType;
    }
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getPlayerID() {
        return playerID;
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public abstract void execute(Object serverObject);
}
