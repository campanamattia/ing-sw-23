package Server.Network.Servers;

import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;

import static Server.ServerApp.executorService;

/**
 * The SocketServer class represents the server that listens for incoming socket connections.
 */
public class SocketServer {
    /**
     * Starts the Socket Server on the specified port.
     *
     * @param socketPort the port number to listen on
     */
    public void start(int socketPort) {
        ServerApp.logger.info("Starting SCK server on " + socketPort);
        try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
            ServerApp.logger.info("SCK server listening " + socketPort + " port");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    executorService.execute(new SocketHandler(socket));
                } catch (IOException e) {
                    ServerApp.logger.log(Level.SEVERE, e.toString());
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        } finally {
            executorService.shutdown();
        }
    }
}
