package Messages.Server.Network;

import Client.Network.Network;
import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;

public class PongMessage extends ServerMessage {
    Network network;

    public PongMessage() {
        this.messageType = MessageType.PONG;
    }

    @Override
    public void execute(View view) {
        network = view.getNetwork();
        // network.pong();
    }
}
