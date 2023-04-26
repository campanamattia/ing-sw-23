package Server.Network.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;
import Messages.ServerMessage;
import Server.Controller.GameController;
import Server.Network.ClientHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientHandlerSocket extends ClientHandler {
    private final Socket socket;

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
                    ClientMessage input = deserialize(line);
                    ServerMessage output = GameController.doAction(input);

                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
