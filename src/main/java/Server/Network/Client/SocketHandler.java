package Server.Network.Client;

import Messages.ClientMessage;
import Messages.ServerMessage;
import Server.Controller.GameController;
import Server.Controller.PlayerAction;
import Server.Network.ClientHandler;
import Server.Network.Lobby;
import Utils.ClientMessageFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Scanner;


public class SocketHandler extends ClientHandler implements Runnable{
    private final Socket socket;
    private final Lobby lobby;
    private PlayerAction playerAction;

    public SocketHandler(Socket socket, Lobby lobby, PlayerAction playerAction) {
        this.playerID = null;
        this.playerAction = null;
        this.socket = socket;
        this.lobby = lobby;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            while (true) {
                String line = in.nextLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    ClientMessage input = deserialize(line);
                    ServerMessage output = GameController.doAction(input);
                    send(output);
                }
            }
            in.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void send(ServerMessage output) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(output);
        out.flush();
    }

    private ClientMessage deserialize(String line) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(line));
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        return ClientMessageFactory.getClientMessage(jsonObject.get("OperationType").getAsString(), line);
    }

    public Lobby getLobby() {
        return lobby;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }
    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }
}
