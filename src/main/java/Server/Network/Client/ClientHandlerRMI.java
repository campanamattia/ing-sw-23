package Server.Network.Client;

import Server.Network.ClientHandler;

import javax.management.remote.rmi.RMIConnector;

public class ClientHandlerRMI extends ClientHandler {
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
