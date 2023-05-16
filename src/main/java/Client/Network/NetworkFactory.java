package Client.Network;

import java.io.IOException;

import Client.View.*;

public class NetworkFactory {
    public static Network instanceNetwork(String network, View view) {
        if (network.equals("RMI")) {
            return new ClientRMI(view);
        }
        return new ClientSocket(view);
    }
}
