package Server;


import Server.Network.Lobby.Lobby;
import Server.Network.Servers.ServerRMI;
import Server.Network.Servers.SocketServer;
import Utils.NetworkSettings;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Server.TXTFormatter.dateFormatter;


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
     * The ip address of the server.
     */
    private static String ipHost;
    /**
     * The port number for the socket server.
     */
    private static int socketPort = 0;
    /**
     * The port number for the RMI server.
     */
    private static int rmiPort = 0;

    public static final ReentrantLock lock = new ReentrantLock();

    /**
     * The main method that starts the server application.
     *
     * @param args the command-line arguments (optional socket and RMI ports)
     */
    public static void main(String[] args) {
        initLogger();

        if (args.length < 1) {
            logger.severe("USAGE: java -jar --enable-preview MSH-SERVER-v1.01.jar <ipHost> [<socketPort> <rmiPort>]");
            System.exit(-1);
        }
        ipHost = args[0];
        if (!isValid()) {
            logger.severe("Invalid ipHost");
            System.exit(-2);
        }
        logger.info("SERVER STARTED ON: " + ipHost );
        initLobby();

        Scanner scanner = new Scanner(System.in);
        executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            while (true) {
                String input = scanner.nextLine();
                switch (input) {
                    case "exit" -> System.exit(0);
                    case "status" -> lobby.printLobbyStatus();
                    default -> logger.fine("Unknown command");
                }
            }
        });

        setPort(args);

        Thread rmiThread = new Thread(ServerApp::rmiServer);
        rmiThread.start();

        Thread socketThread = new Thread(ServerApp::socketServer);
        socketThread.start();

        logger.info("ServerApp started");
    }

    private static boolean isValid() {
        switch (ipHost) {
            case "l", "localhost" -> {
                ipHost = "127.0.0.1";
                return true;
            }
            case "d", "default" -> {
                ipHost = NetworkSettings.ipHostFromJSON();
                return true;
            }
            default -> {
                String[] ip = ipHost.split("\\.");
                if (ip.length != 4) return false;
                for (String s : ip) {
                    int i = Integer.parseInt(s);
                    if (i < 0 || i > 255) return false;
                }
                return true;
            }
        }
    }

    private static void initLogger() {
        logger = Logger.getLogger(ServerApp.class.getName());
        try {
            FileHandler fileHandler = new FileHandler("log.txt");
            fileHandler.setFormatter(new TXTFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.exit(-3);
        }
        logger.setLevel(Level.ALL);
        logger.info("MY SHELFIE SERVER LOG " + LocalDateTime.now().format(dateFormatter) + "\n");
    }

    private static void initLobby() {
        try {
            lobby = new Lobby();
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
            System.exit(-4);
        }
    }

    private static void setPort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            try {
                if (args[i].equals("-s")) {
                    i++;
                    socketPort = ((Integer.parseInt(args[i]) >= 1024) && (Integer.parseInt(args[i]) <= 65535)) ? Integer.parseInt(args[i]) : 0;
                } else if (args[i].equals("-r")) {
                    i++;
                    rmiPort = ((Integer.parseInt(args[i]) >= 1024) && (Integer.parseInt(args[i]) <= 65535)) ? Integer.parseInt(args[i]) : 0;
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                logger.log(Level.SEVERE, e.getMessage());
                System.exit(-5);
            }
        }

        if (socketPort == 0) socketPort = NetworkSettings.socketFromJSON();
        if (rmiPort == 0) rmiPort = NetworkSettings.rmiFromJSON();
    }

    private static void rmiServer() {
        try {
            new ServerRMI().start(ipHost, rmiPort);
        } catch (RemoteException | AlreadyBoundException e) {
            logger.log(Level.SEVERE, e.toString());
            System.exit(-6);
        }
    }

    private static void socketServer() {
        new SocketServer().start(socketPort);
    }
}


/**
 * The TXTFormatter class represents a custom formatter for the logger.
 */
class TXTFormatter extends Formatter {
    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Formats the log record.
     *
     * @param record the log record to format
     * @return the formatted log record
     */
    @Override
    public String format(LogRecord record) {

        return dateFormatter.format(LocalDateTime.now()) + " " +

                "[" + record.getLevel().toString() + "] " +

                record.getMessage() ;
    }
}
