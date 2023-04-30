package Client.Network;

import java.io.IOException;

public class NetworkFactory {
    public static Network getNetwork(String network) throws IOException {
        switch (network) {
            case "SOCKET" -> {
                return new ClientSocket();
            }
            case "RMI" -> {
                return new ClientRMI();
            }
        }
        return null;
    }
}
