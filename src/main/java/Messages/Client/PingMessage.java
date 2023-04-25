package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;

public class PingMessage extends ClientMessage {

    public PingMessage(){
        super();
    }

    public PingMessage(String playerID){
        this.operationType = OperationType.PING;
        this.playerID = playerID;
    }
}
