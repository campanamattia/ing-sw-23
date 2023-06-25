package Server;


import Server.Network.Lobby.Lobby;
import Server.Network.Servers.ServerRMI;
import Server.Network.Servers.SocketServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The ServerApp class represents the main entry point for the server application.
 * It initializes the logger, sets the server ports, initializes the lobby,
 * starts the RMI server and the socket server, and logs the server status.
 */
public class ServerApp {
    /**
     * The logger instance for logging server activity.
     */
    public static Logger logger;
    /**
     * The lobby instance for managing client connections and games.
     */
    public static Lobby lobby;
    /**
     * The executor service for managing threads.
     */
    public static ExecutorService executorService;
    /**
     * The file path for the server setting JSON file.
     */
    private static final String serverSetting = "settings/serverSetting.json";
    /**
     * The port number for the socket server.
     */
    private static int socketPort = 0;
    /**
     * The port number for the RMI server.
     */
    private static int rmiPort = 0;

    /**
     * The main method that starts the server application.
     *
     * @param args the command-line arguments (optional socket and RMI ports)
     */
    public static void main(String[] args) {
        initLogger();
        initLobby();
        executorService = Executors.newCachedThreadPool();

        setPort(args);
        // Start the RMI server in a new thread.
        Thread rmiThread = new Thread(ServerApp::rmiServer);
        rmiThread.start();

        // Start the socket server in a new thread.
        Thread socketThread = new Thread(ServerApp::socketServer);
        socketThread.start();

        logger.info("ServerApp started");
    }

    private static void initLogger() {
        logger = Logger.getLogger(ServerApp.class.getName());
        try {
            logger.addHandler(new FileHandler("logger.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.setLevel(Level.ALL);
        logger.info("Starting ServerApp");
    }

    private static void setPort(String[] args) {
        try {
            switch (args.length) {
                case 2 -> {
                    socketPort = Integer.parseInt(args[0]);
                    rmiPort = Integer.parseInt(args[1]);
                }
                case 1 -> {
                    socketPort = Integer.parseInt(args[0]);
                    rmiPort = rmiFromJSON();
                }
                default -> {
                    socketPort = socketFromJSON();
                    rmiPort = rmiFromJSON();
                }
            }
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
    }

    private static void initLobby() {
        try {
            lobby = new Lobby();
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static int socketFromJSON() throws RuntimeException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(serverSetting))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get("socketPort").getAsInt();
    }

    @SuppressWarnings("ConstantConditions")
    private static int rmiFromJSON() throws RuntimeException {
        Gson gson = new Gson();
        JsonReader reader;
        reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(serverSetting))));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        return json.get("rmiPort").getAsInt();
    }

    private static void rmiServer() {
        try {
            new ServerRMI().start(rmiPort);
        } catch (RemoteException | AlreadyBoundException e) {
            logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
    }

    private static void socketServer() {
        new SocketServer().start(socketPort);
    }
}


