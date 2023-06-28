package Server.Network.Servers;

import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;

import static Server.ServerApp.executorService;
import static Server.ServerApp.lock;

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
        lock.lock();
        ServerApp.logger.info("Starting SCK server on " + socketPort);
        try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
            ServerApp.logger.info("SCK server listening " + socketPort + " port");
            lock.unlock();
            while (true) {
                try {
                    @SuppressWarnings("BlockingMethodInNonBlockingContext") Socket socket = serverSocket.accept();
                    executorService.execute(new SocketHandler(socket));
                } catch (IOException e) {
                    ServerApp.logger.log(Level.SEVERE, e.toString());
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.toString());
            System.exit(-7);
        } finally {
            executorService.shutdown();
        }
    }
}
