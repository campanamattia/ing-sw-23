package Client.Network;

import java.io.IOException;
import Client.View.*;

public class NetworkFactory {
    public static Network getNetwork(String network,View view) throws IOException {
        switch (network) {
            case "SOCKET" -> {
                return new ClientSocket(view);
            }
            case "RMI" -> {
                return new ClientRMI(view);
            }
        }
        return null;
    }
}
