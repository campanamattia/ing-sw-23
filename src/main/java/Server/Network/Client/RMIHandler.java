package Server.Network.Client;

import Interface.RemoteView;
import Server.Network.ClientHandler;

public class RMIHandler extends ClientHandler {
    private RemoteView virtualRemoteView;

    public RMIHandler(String playerID, RemoteView remoteView) {
        this.playerID = playerID;
        this.virtualRemoteView = remoteView;
    }

    @Override
    public String getPlayerID() {
        return super.getPlayerID();
    }
}
