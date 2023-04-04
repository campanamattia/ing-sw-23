package Server.Network;

import javax.management.remote.rmi.RMIConnector;
import java.net.Socket;
import java.util.List;

public class InputHandler {
    private List<PlayerHandler> connection;

    //pool of connection

    public void addPlayer (Socket connection){

    }
    public void addPlayer (RMIConnector connection){

    }

    public List<PlayerHandler> getConnection() {
        return connection;
    }
}
