package Server.Network.Client;

import Interface.Client.RemoteView;

public class RMIHandler extends ClientHandler {
    private final RemoteView virtualRemoteView;

    public RMIHandler(String playerID, RemoteView remoteView) {
        this.playerID = playerID;
        this.virtualRemoteView = remoteView;

    }

    @Override
    public String getPlayerID() {
        return super.getPlayerID();
    }
}
