package Client.Network;

import Client.View.View;
import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Server.Controller.GameController;
import Utils.ChatMessage;
import Utils.Coordinates;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Stack;

public class ClientRMI extends Network {
    private GameCommand gc;
    private LobbyInterface lobby;

    public ClientRMI(View view) {
        super(view);
    }

    @Override
    public void init(String ip, int port) throws IOException {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, port);
            this.lobby = (LobbyInterface) registry.lookup("LobbyInterface");
        } catch (Exception e) {
            try {
                view.outcomeException(e);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        this.gc.selectTiles(playerID, coordinates);

    }

    @Override
    public void writeChat(String playerID, String text) throws RemoteException {
        this.gc.writeChat(playerID, text);
    }

    @Override
    public void insertTiles(String playerID, List<Integer> sorted, int column) throws RemoteException {
        this.gc.insertTiles(playerID, sorted, column);
    }

    @Override
    public void addSubscriber(Object object) throws RemoteException {
        this.gc.addSubscriber(object);
    }

    @Override
    public void getLobbyInfo(RemoteView remote) throws RemoteException {
        this.lobby.getLobbyInfo(remote);
    }

    @Override
    public void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        this.lobby.setLobbySize(playerID, lobbyID, lobbySize);
    }

    @Override
    public void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient client) throws RemoteException {
        this.lobby.login(playerID, lobbyID, remoteView, (RemoteClient) this);
    }

    @Override
    public void ping(String playerID, String lobbyID) throws RemoteException {
        this.lobby.ping(playerID, lobbyID);
    }

    @Override
    public void getGameController(String lobbyID, RemoteClient remote) throws RemoteException {
        try {
            this.lobby.getGameController(lobbyID, remote);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logOut(String playerID, String lobbyID) throws RemoteException {
        this.lobby.logOut(playerID, lobbyID);
    }

    @Override
    public void pong() throws RemoteException {

    }

    @Override
    public void setGameController(GameController gameController) throws RemoteException {
        this.gc = gameController;
    }

    @Override
    public void update(Object objects) throws RemoteException {
        if(scouts.containsKey(objects.getClass())){
            scouts.get(objects.getClass()).update(objects);
        } else {
            throw new RemoteException("Scout not found");
        }
    }
}
