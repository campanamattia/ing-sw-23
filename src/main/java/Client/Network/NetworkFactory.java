package Client.Network;

import java.rmi.RemoteException;

import Client.View.*;

public class NetworkFactory {
    public static Network instanceNetwork(String network, View view) throws RemoteException {
        if (network.equalsIgnoreCase("RMI")) {
            return new ClientRMI(view);
        }
        else if (network.equalsIgnoreCase("CLI")) {
            return new ClientSocket(view);
        }
        return null;
    }
}
