package Client;

import Client.Network.Network;
import Client.View.*;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {

    public static final String STYLEPATH = String.valueOf(ClientApp.class.getResource("/css/style.css"));
    public static String localPlayer;
    public static String lobbyID;

    public static View view;
    public static Network network;
    public static ExecutorService executorService;

    public static void main (String[] args) throws RemoteException {
        executorService = Executors.newCachedThreadPool();
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("GUI")){
                view = ViewFactory.instanceView(args[0]);
            }
            else
                view = ViewFactory.instanceView("CLI");
        }
    }
}
