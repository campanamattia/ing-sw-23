package Server.Network.Player;

import Enumeration.OperationType;
import Messages.ServerMessage;
import Server.Network.PlayerHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientHandlerSocket extends PlayerHandler {
    private Socket socket;
    public ClientHandlerSocket(String playerID, Socket socket) {
        this.playerID = playerID;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            while (true) {
                String line = in.nextLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    out.println("Received: " + line);
                    out.flush();
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public OperationType deserializer(Socket connection){
        return null;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void send(ServerMessage serverMessage) {
    }
}
