package Server.Network;

import Server.Controller.PlayerAction;
import Server.ServerApp;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI {

    private static Registry registry;

    public void start(Lobby lobby, int rmiPort) throws RemoteException, AlreadyBoundException {
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

    public static void addBind(UUID uuid, PlayerAction playerAction){
        try {
            ServerRMI.registry.bind("GameCommand_"+uuid.toString(), playerAction);
        } catch (RemoteException | AlreadyBoundException e) {
            ServerApp.logger.log(Level.SEVERE, e.toString());
            throw new RuntimeException(e);
        }
    }
}
