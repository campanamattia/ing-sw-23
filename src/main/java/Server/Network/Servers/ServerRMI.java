package Server.Network.Servers;

import Server.Network.Lobby.Lobby;
import Server.ServerApp;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

public class ServerRMI {

    private static Registry registry;

    public void start(Lobby lobby, int rmiPort) throws RemoteException, AlreadyBoundException {
        ServerApp.logger.info("Starting RMI server");
        ServerRMI.registry = LocateRegistry.createRegistry(rmiPort);
        try {
            registry.bind("Lobby", lobby);
        }
        catch (Exception e) {
            ServerApp.logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
        ServerApp.logger.info("Server RMI ready");
    }
}
