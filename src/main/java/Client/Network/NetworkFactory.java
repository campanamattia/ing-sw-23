package Client.Network;

public class NetworkFactory {
    public static Network getNetwork(String network){
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
