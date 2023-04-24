package Messages.Client;

import Client.View.View;
import Enumeration.OperationType;
import Messages.ClientMessage;

public class ChangeView extends ClientMessage {
    String view;

    public ChangeView(OperationType operationType, String playerID, String view) {
        this.operationType = operationType;
        this.playerID = playerID;
        this.view = view;
    }
}
