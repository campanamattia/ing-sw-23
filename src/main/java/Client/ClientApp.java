package Client;

import Client.Network.Network;
import Client.View.*;
import Utils.NetworkSettings;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {

    public static final String STYLEPATH = String.valueOf(ClientApp.class.getResource("/css/style.css"));
    public static String localPlayer;
    public static String lobbyID;

    public static String IP_SERVER;
    public static int SOCKET_PORT;
    public static int RMI_PORT;

    public static View view;
    public static Network network;
    public static ExecutorService executorService;

    public static void main (String[] args) throws RemoteException {
        executorService = Executors.newCachedThreadPool();
        setDefault();

        if (args.length != 1){
            System.out.println("Usage: java -jar --enable-preview MyShelfie.jar <GUI/CLI>");
            System.exit(-1);
        }

        switch(args[0]){
            case "GUI", "gui" -> view = ViewFactory.instanceView("GUI");
            case "CLI", "cli" -> view = ViewFactory.instanceView("CLI");
            default -> {
                System.out.println("Usage: java -jar --enable-preview MyShelfie.jar <GUI/CLI>");
                System.exit(-1);
            }
        }
    }

    private static void setDefault() {
        IP_SERVER = NetworkSettings.ipHostFromJSON();
        SOCKET_PORT = NetworkSettings.socketFromJSON();
        RMI_PORT = NetworkSettings.rmiFromJSON();
    }
}
