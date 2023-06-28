package Client;

import Client.Network.Network;
import Client.View.*;
import Utils.NetworkSettings;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The main client application class responsible for launching the game client.
 */
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

    /**
     * The main entry point for the game client application.
     *
     * @param args Command line arguments (optional).
     * @throws RemoteException If an exception occurs during remote communication.
     */
    public static void main (String[] args) throws RemoteException {
        executorService = Executors.newCachedThreadPool();
        setDefault();
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("GUI")){
                view = ViewFactory.instanceView(args[0]);
            }
            else
                view = ViewFactory.instanceView("CLI");
        }
    }

    /**
     * Sets the default network settings based on the configuration file.
     */
    private static void setDefault() {
        IP_SERVER = NetworkSettings.ipHostFromJSON();
        SOCKET_PORT = NetworkSettings.socketFromJSON();
        RMI_PORT = NetworkSettings.rmiFromJSON();
    }
}
