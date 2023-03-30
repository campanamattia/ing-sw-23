package Server.Network.Player;

import Server.Network.PlayerHandler;

import javax.management.remote.rmi.RMIConnector;

public class ClientHandlerRMI extends PlayerHandler {
    // TODO: 29/03/2023
    private RMIConnector connection;

    public ClientHandlerRMI(String playerID, RMIConnector connection) {
        this.playerID = playerID;
        this.connection = connection;
    }

    @Override
    public void run() {

    }

    @Override
    public String getPlayerID() {
        return super.getPlayerID();
    }
}
