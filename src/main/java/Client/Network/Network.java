package Client.Network;

import Interface.Client.RemoteClient;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Interface.Scout;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;
import Client.Network.Scouts.BoardScout;
import Client.Network.Scouts.ChatScout;
import Client.Network.Scouts.CommonGoalScout;
import Client.Network.Scouts.PlayerScout;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import static Client.ClientApp.*;

/**
 The {@code Network} class is an abstract class that represents a network connection for a client application.
 It extends the {@link java.rmi.server.UnicastRemoteObject} class and implements various interfaces related to
 game commands, lobby interface, remote client, and scout.
 It provides methods for initializing the network connection, starting ping/pong communication, updating objects,
 and handling timeouts.
 */
@SuppressWarnings("rawtypes")
public abstract class Network extends UnicastRemoteObject implements GameCommand, LobbyInterface, RemoteClient, Scout {

    protected HashMap<Class<?>, Scout> scouts;
    protected Timer timer;

    /**
     Constructs a new {@code Network} object, add all the scout and create a new timer.
     @throws RemoteException if a remote communication error occurs
     */
    @SuppressWarnings("BlockingMethodInNonBlockingContext")
    public Network() throws RemoteException {
        super();
        this.scouts = new HashMap<>();
        scouts.put(MockBoard.class, new BoardScout());
        scouts.put(ChatMessage.class, new ChatScout());
        scouts.put(MockPlayer.class, new PlayerScout());
        scouts.put(MockCommonGoal.class, new CommonGoalScout());
        this.timer = new Timer();
    }

    /**
     Initializes the network connection.
     */
    public abstract void init();

    /**
     Starts the ping/pong communication with the specified player and lobby.
     @param playerID the ID of the player
     @param lobbyID the ID of the lobby
     */
    public void startPing(String playerID, String lobbyID) {
        try {
            ping(playerID, lobbyID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if (timer == null) timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                quit(404);
            }
        }, 10000); //15-seconds timeout
    }

    /**
     Updates the objects received from the server.
     @param objects the objects to be updated
     @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void update(Object objects) throws RemoteException {
        if (scouts.containsKey(objects.getClass())) {
            scouts.get(objects.getClass()).update(objects);
        } else {
            view.outcomeException(new RuntimeException("Scout-handler not found"));
        }
    }

    /**
     Receives a pong message from the server to confirm the connection.
     Cancels the timer for timeout and schedules a new ping request.
     @param playerID the ID of the player
     @param lobbyID the ID of the lobby
     @throws RemoteException if a remote communication error occurs
     */
    @SuppressWarnings("BlockingMethodInNonBlockingContext")
    @Override
    public void pong(String playerID, String lobbyID) throws RemoteException {
        if (timer != null) timer.cancel();
        timer = null;
        executorService.execute(() -> {
            try {
                Thread.sleep(7000);
                startPing(playerID, lobbyID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}