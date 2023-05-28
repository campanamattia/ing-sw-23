package Server.Network.Lobby;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import Interface.Client.RemoteClient;
import Server.ServerApp;

public class PingTimer{
    private Timer timer;
    private final String clientID;
    private final String lobbyID;
    private final RemoteClient client;

    public PingTimer(String clientID, String lobbyID, RemoteClient client) {
        this.clientID = clientID;
        this.lobbyID = lobbyID;
        this.client = client;
    }

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
        }, 15000); //15 seconds timeout
    }

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
        finally {
            start();
        }
    }

    public void interrupt() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public RemoteClient getClient() {
        return client;
    }
}
