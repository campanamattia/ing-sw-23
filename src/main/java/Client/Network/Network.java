package Client.Network;

import Interface.Client.RemoteClient;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Client.View.View;
import Server.Model.Talent.*;
import Utils.Scouts.ChatScout;
import Utils.Scouts.CommonGoalScout;
import Utils.Scouts.PlayerScout;
import Utils.Scouts.BoardScout;
import Interface.Scout;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class Network implements GameCommand, LobbyInterface, RemoteClient, Scout{
    protected View view;

    protected String ipAddress;
    protected int port;

    protected Map<Class<? extends Talent>, Scout> scouts;
    protected Timer timer;
    protected ExecutorService executor;

    public Network(View view) {
        this.view = view;
        this.scouts = new HashMap<>();
        this.timer = null;
        this.executor = Executors.newCachedThreadPool();
        this.scouts.put(PlayerTalent.class, new PlayerScout(this.view));
        this.scouts.put(BoardTalent.class, new BoardScout(this.view));
        this.scouts.put(CommonGoalTalent.class, new CommonGoalScout(this.view));
        this.scouts.put(ChatTalent.class, new ChatScout(this.view));
    }

    public abstract void init (String ipAddress, int ip) throws IOException;

    public void startPing(String playerID, String lobbyID){
        if(timer == null)
            timer = new Timer();

        this.executor.submit(()->{
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        ping(playerID, lobbyID);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            },0, 5000); // 5 seconds timeout
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Object objects) throws RemoteException {
        if(scouts.containsKey(objects.getClass())){
            scouts.get(objects.getClass()).update(objects);
        } else {
            throw new RemoteException("Scout-handler not found");
        }
    }

    @Override
    public void pong(String playerID, String lobbyID) throws RemoteException {
        if(timer != null)
            timer.cancel();
        timer = null;
        startPing(playerID, lobbyID);
    }
}