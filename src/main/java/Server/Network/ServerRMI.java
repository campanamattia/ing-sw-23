package Server.Network;

import Server.Controller.PlayerAction;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI {

    public void start(Logger logger, PlayerAction playerAction, int rmiPort) throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(rmiPort);
        try {
            registry.bind("ChatService", playerAction);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            throw e;
        }
        logger.info("Server RMI ready");

    }
}
