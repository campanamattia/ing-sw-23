package Client.Network;

import java.rmi.RemoteException;

import Client.View.*;


/**
 The {@code NetworkFactory} class is a factory class for creating network instances based on the specified network type.
 It provides a static method to create an instance of the network based on the given network type.
 */
public class NetworkFactory {

    /**
     Creates an instance of the network based on the specified network type.
     @param network the network type ("RMI" or "SOCKET")
     @return an instance of the network
     @throws RemoteException if a remote communication error occurs
     */
    public static Network instanceNetwork(String network) throws RemoteException {
        if (network.equalsIgnoreCase("RMI")) {
            return new ClientRMI();
        }
        else if (network.equalsIgnoreCase("SOCKET")) {
            return new ClientSocket();
        }
        return null;
    }
}
