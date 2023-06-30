package Server.Network.Servers;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

import static Server.ServerApp.*;

/**
 * The ServerRMI class represents the server that listens for incoming RMI connections.
 */
@SuppressWarnings("BlockingMethodInNonBlockingContext")
public class ServerRMI {
    /**
     * The registry instance for the RMI server.
     */
    private static Registry registry;

    /**
     * Starts the RMI server on the specified port.
     *
     * @param rmiPort the port number to listen on
     * @throws RemoteException       if the RMI server cannot be started
     * @throws AlreadyBoundException if the RMI server is already bound
     */
    public void start(String ipHost, int rmiPort) throws RemoteException{
        lock.lock();
        logger.info("Starting RMI server on " + rmiPort);
        System.setProperty("java.rmi.server.hostname", ipHost);
        ServerRMI.registry = LocateRegistry.createRegistry(rmiPort);
        try {
            registry.bind("Lobby", lobby);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            System.exit(-6);
        }
        logger.info("RMI server listening " + rmiPort + " port");
        lock.unlock();
    }
}
