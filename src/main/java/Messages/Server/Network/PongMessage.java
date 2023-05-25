package Messages.Server.Network;

import Client.Network.Network;
import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;

public class PongMessage extends ServerMessage {
    private final String playerID;
    private final String lobbyID;

    public PongMessage(String playerID, String lobbyID) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute(View view) {
        Network network = view.getNetwork();
        try {
            network.pong(playerID, lobbyID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
