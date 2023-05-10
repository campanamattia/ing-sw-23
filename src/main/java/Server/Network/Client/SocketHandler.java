package Server.Network.Client;

import Interface.Scout.BoardScout;
import Interface.Scout.CommonGoalScout;
import Interface.Scout.PlayerScout;
import Messages.ClientMessage;
import Messages.Server.ErrorMessage;
import Messages.ServerMessage;
import Server.Controller.Players.PlayersHandler;
import Server.Network.Lobby;
import Server.Network.LogOutTimer;
import Utils.ClientMessageFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SocketHandler extends ClientHandler implements Runnable, PlayerScout, BoardScout, CommonGoalScout {
    private final Socket socket;
    private final ExecutorService executorService;
    private final Lobby lobby;
    private PlayersHandler playersHandler;

    public SocketHandler(Socket socket, Lobby lobby) {
        this.playerID = null;
        this.playersHandler = null;
        this.executorService = Executors.newCachedThreadPool();
        this.socket = socket;
        this.lobby = lobby;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            Timer timer = new Timer();

            while (true) {
                String line = in.nextLine();
                timer.cancel();
                timer = new Timer();
                timer.schedule(new LogOutTimer(this.playersHandler, this.playerID),10000);

                if (line.equals("quit")) {
                    break;
                } else {
                    this.executorService.submit(() -> {
                        ClientMessage input = deserialize(line);
                        try {
                            if (playersHandler != null)
                                input.execute(playersHandler);
                            else if (lobby != null)
                                input.execute(lobby);
                            else
                                send(new ErrorMessage(new RuntimeException("Server is not ready yet")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
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

    public PlayersHandler getPlayerAction() {
        return playersHandler;
    }
}
