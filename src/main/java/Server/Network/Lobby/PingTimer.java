package Server.Network.Lobby;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import Interface.Client.RemoteClient;
import Server.ServerApp;

public class PingTimer {
    private Timer timer;
    private final String clientID;
    private final String lobbyID;
    private final RemoteClient client;

    /**
     * Creates a new PingTimer instance.
     *
     * @param clientID the ID of the client
     * @param lobbyID  the ID of the lobby
     * @param client   the remote client object
     */
    public PingTimer(String clientID, String lobbyID, RemoteClient client) {
        this.clientID = clientID;
        this.lobbyID = lobbyID;
        this.client = client;
    }

    /**
     * Starts the ping timer.
     * If a timer is already running, it will be cancelled and a new timer will be created.
     */
    public void start() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ServerApp.lobby.logOut(clientID, lobbyID);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 10000); // 10 seconds timeout
    }

    /**
     * Receives a ping from the client and resets the timer.
     * If a timer is running, it will be cancelled and a new timer will be started.
     */
    public void receivedPing() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;

        try {
            this.client.pong(this.clientID, this.lobbyID);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.getMessage());
        }

        start();
    }

    /**
     * Interrupts the timer.
     * If a timer is running, it will be cancelled.
     */
    public void interrupt() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Returns the remote client object associated with the timer.
     *
     * @return the remote client object
     */
    public RemoteClient getClient() {
        return client;
    }
}
