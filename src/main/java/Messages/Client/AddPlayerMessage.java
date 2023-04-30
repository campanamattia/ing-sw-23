package Messages.Client;

import Client.Network.Network;
import Enumeration.OperationType;
import Messages.ClientMessage;

public class AddPlayerMessage extends ClientMessage {

    public AddPlayerMessage(String playerID) {
        this.operationType = OperationType.ADDPLAYER;
        this.playerID = playerID;
    }
}
