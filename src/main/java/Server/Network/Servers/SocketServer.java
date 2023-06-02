package Server.Network.Servers;

import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;

import static Server.ServerApp.executorService;

public class SocketServer {

    public void start(int socketPort) {
        ServerApp.logger.info("Starting Server Socket");
        try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
            ServerApp.logger.info("Server Socket ready on port " + socketPort);
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
