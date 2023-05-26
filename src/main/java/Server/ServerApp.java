package Server;


import Server.Network.Lobby.Lobby;
import Server.Network.Servers.ServerRMI;
import Server.Network.Servers.SocketServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The ServerApp class represents the main entry point for the server application.
 * It initializes the logger, sets the server ports, initializes the lobby,
 * starts the RMI server and the socket server, and logs the server startup.
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
     * The file path for the server setting JSON file.
     */
    private static final String serverSetting = "/settings/serverSetting.json";
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
        setPort(args);
        initLobby();

        // Start the RMI server in a new thread.
        Thread rmiThread = new Thread(ServerApp::rmiServer);
        rmiThread.start();

        // Start the socket server in a new thread.
        Thread socketThread = new Thread(ServerApp::socketServer);
        socketThread.start();

        logger.info("ServerApp started");
    }

    /**
     * Initializes the logger and adds a FileHandler for logging to a file.
     * The logger is set to log all levels of messages.
     */
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

    /**
     * Sets the socket and RMI ports based on the command-line arguments.
     * If no arguments are provided, the ports are read from the server setting JSON file.
     *
     * @param args the command-line arguments
     */
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

    /**
     * Initializes the lobby instance.
     * It creates a new Lobby object for managing client connections and games.
     */
    private static void initLobby() {
        try {
            lobby = new Lobby();
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
    }

    /**
     * Reads the socket port number from the server setting JSON file.
     *
     * @return the socket port number
     * @throws RuntimeException if the server setting JSON file is not found
     */
    @SuppressWarnings("ConstantConditions")
    private static int socketFromJSON() throws RuntimeException {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(ServerApp.class.getResource(serverSetting).getFile()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            return json.get("socketPort").getAsInt();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the RMI port number from the server setting JSON file.
     *
     * @return the RMI port number
     * @throws RuntimeException if the server setting JSON file is not found
     */
    @SuppressWarnings("ConstantConditions")
    private static int rmiFromJSON() throws RuntimeException {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(ServerApp.class.getResource(serverSetting).getFile()));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            return json.get("rmiPort").getAsInt();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the RMI server by creating a new ServerRMI instance and binding it to the specified RMI port.
     * It handles RemoteException and AlreadyBoundException by logging the error and exiting the application.
     */
    private static void rmiServer() {
        try {
            new ServerRMI().start(lobby, rmiPort);
        } catch (RemoteException | AlreadyBoundException e) {
            logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
    }

    /**
     * Starts the socket server by creating a new SocketServer instance and starting it with the specified socket port.
     */
    private static void socketServer() {
        new SocketServer().start(socketPort);
    }
}


