package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;
import Server.Model.Player;

public class WriteChat extends ClientMessage {
    String messageText;

    public WriteChat(OperationType operationType, String playerID, String messageText) {
        this.operationType = operationType;
        this.playerID = playerID;
        this.messageText = messageText;
    }
}
