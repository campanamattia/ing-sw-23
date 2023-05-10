package Server.Network;

import Server.Controller.Players.PlayersHandler;

import java.rmi.RemoteException;
import java.util.TimerTask;

public class LogOutTimer extends TimerTask implements Runnable{
    private final PlayersHandler playersHandler;
    private final String playerID;

    public LogOutTimer(PlayersHandler playersHandler, String playerID) {
        this.playersHandler = playersHandler;
        this.playerID = playerID;
    }

    @Override
    public void run() {
        try {
            this.playersHandler.logOut(this.playerID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
