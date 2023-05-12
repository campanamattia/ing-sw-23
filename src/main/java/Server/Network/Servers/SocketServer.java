package Server.Network.Servers;

import Server.Network.Client.SocketHandler;
import Server.Network.Lobby.Lobby;
import Server.ServerApp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.net.*;
import java.util.logging.Level;

public class SocketServer {

    public void start(Lobby lobby, int socketPort){
        ServerApp.logger.info("Starting Server Socket");
        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
            ServerApp.logger.info("Server Socket ready");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    executor.submit(new SocketHandler(socket, lobby));
                } catch(IOException e) {
                    ServerApp.logger.log(Level.SEVERE, e.toString());
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        } finally {
            executor.shutdown();
        }
    }
}
