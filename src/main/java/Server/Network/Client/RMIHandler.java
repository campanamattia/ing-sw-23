package Server.Network.Client;

import Interface.View;
import Server.Network.ClientHandler;

import javax.management.remote.rmi.RMIConnector;

public class RMIHandler extends ClientHandler {
    private View virtualView;

    public RMIHandler(String playerID, View view) {
        this.playerID = playerID;
        this.virtualView = view;
    }

    @Override
    public String getPlayerID() {
        return super.getPlayerID();
    }
}
