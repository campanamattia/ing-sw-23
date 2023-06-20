package Server.Network.Servers;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

import static Server.ServerApp.lobby;
import static Server.ServerApp.logger;

/**
 * The ServerRMI class represents the server that listens for incoming RMI connections.
 */
public class ServerRMI {
    /**
     * The registry instance for the RMI server.
     */
    private static Registry registry;

    /**
     * Starts the RMI server on the specified port.
     * @param rmiPort the port number to listen on
     * @throws RemoteException if the RMI server cannot be started
     * @throws AlreadyBoundException if the RMI server is already bound
     */
    public void start(int rmiPort) throws RemoteException, AlreadyBoundException {
        logger.info("Starting RMI server");
        ServerRMI.registry = LocateRegistry.createRegistry(rmiPort);
        try {
            registry.bind("Lobby", lobby);
            logger.info("Lobby bound correctly");
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            System.exit(-1);
        }
        logger.info("Server RMI ready on port " + rmiPort);
    }
}
