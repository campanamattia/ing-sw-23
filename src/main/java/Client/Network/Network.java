package Client.Network;

import Interface.Client.RemoteClient;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Client.View.View;
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
import static Client.ClientApp.executorService;
import static Client.ClientApp.view;


public abstract class Network extends UnicastRemoteObject implements GameCommand, LobbyInterface, RemoteClient, Scout {

    protected HashMap<Class<?>, Scout> scouts;
    protected Timer timer;

    public Network(View view) throws RemoteException {
        super();
        this.scouts = new HashMap<>();
        scouts.put(MockBoard.class, new BoardScout());
        scouts.put(ChatMessage.class, new ChatScout());
        scouts.put(MockPlayer.class, new PlayerScout());
        scouts.put(MockCommonGoal.class, new CommonGoalScout());
        this.timer = new Timer();
    }

    public abstract void init(String ipAddress, int ip);

    public void startPing(String playerID, String lobbyID) {
        try {
            ping(playerID, lobbyID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(timer == null) timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    logOut(playerID, lobbyID);
                    System.exit(-1);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 10000); //15 seconds timeout
    }

    @Override
    public void update(Object objects) throws RemoteException {
        if (scouts.containsKey(objects.getClass())) {
            scouts.get(objects.getClass()).update(objects);
        } else {
            view.printError("Scout-handler not found");
            throw new RemoteException("Scout-handler not found");
        }
    }

    @Override
    public void pong(String playerID, String lobbyID) throws RemoteException {
        if (timer != null) timer.cancel();
        timer = null;
        executorService.execute(() -> {
            try {
                Thread.sleep(800000000); //7000 //TODO change to 7 seconds
                startPing(playerID, lobbyID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}