package Client.Network;

import java.rmi.RemoteException;

import Client.View.*;

public class NetworkFactory {
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
