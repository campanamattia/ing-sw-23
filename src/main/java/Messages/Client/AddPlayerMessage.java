package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;

public class AddPlayerMessage extends ClientMessage {

    public AddPlayerMessage() {
        super();
    }

    public AddPlayerMessage(String playerID) {
        this.operationType = OperationType.ADDPLAYER;
        this.playerID = playerID;
    }
}
