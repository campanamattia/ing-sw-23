package Messages.Client;

import Client.Network.Network;
import Enumeration.OperationType;
import Messages.ClientMessage;

public class AddPlayerMessage extends ClientMessage {
    Network network;
    Integer port;


    public AddPlayerMessage(Network network, String playerID,Integer port) {
        this.operationType = OperationType.ADDPLAYER;
        this.playerID = playerID;
        this.network = network;
        this.port = port;
    }
}
