package Client.Network;

import Client.View.View;
import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Interface.Client.RemoteView;
import Interface.Scout.BoardScout;
import Interface.Scout.CommonGoalScout;
import Interface.Scout.PlayerScout;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Messages.Client.InsertTilesMessage;
import Messages.Client.WriteChatMessage;
import Messages.ClientMessage;
import Messages.ServerMessage;
import Utils.Coordinates;
import Utils.Tile;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.rmi.registry.Registry;
import java.util.UUID;

public class ClientRMI extends Network implements GameCommand, LobbyInterface {
    private GameCommand gc;
    private Registry registry;
    private LobbyInterface lobby;
    private View view;
    public ClientRMI(View view){
        this.view = view;
    }

    @Override
    public void init(int port, String ip) throws IOException {
        try{
            registry = LocateRegistry.getRegistry(ip, port);
            this.lobby = (LobbyInterface) registry.lookup("LobbyInterface");
        } catch(Exception e){
            try {
                view.outcomeException(e);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void newGame(UUID uuid) throws NotBoundException, RemoteException {
        this.gc = (GameCommand) registry.lookup(uuid.toString());
        // this.gc.registerListener(this)
    }

    public void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        this.gc.selectedTiles(playerID, coordinates);

    }

    public void selectedTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        this.gc.selectedTiles(playerID, coordinates);
    }

    public void writeChat(String playerID, String text) throws RemoteException {
        this.gc.writeChat(playerID, text);
    }

    public void insertTiles(String playerID, List<Integer> sorted, int column) throws RemoteException {
        this.gc.insertTiles(playerID, sorted, column);
    }

    public void sendMessage(ClientMessage clientMessage) throws IOException{
    }

    public void liveStatus(String playerID) throws RemoteException{
        this.gc.liveStatus(playerID);
    }

    public void addSubscriber(Object object) throws RemoteException{
        this.gc.addSubscriber(object);
    }

    public void getLobbyInfo(RemoteView remote) throws RemoteException{
        this.lobby.getLobbyInfo(remote);
    }

    public void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException{
        this.lobby.setLobbySize(playerID, lobbyID, lobbySize);
    }
    public void logIn(String playerID, String lobbyID, RemoteView remoteView) throws RemoteException{
        this.lobby.logIn(playerID, lobbyID, remoteView);
    }

    public void ping(String playerID, String lobbyID) throws RemoteException{
        this.lobby.ping(playerID, lobbyID);
    }

    public void getGameController(String lobbyID, RemoteClient remote) throws RemoteException{
        this.lobby.getGameController(lobbyID, remote);
    }

    public void logOut(String playerID, String lobbyID) throws RemoteException {
        this.lobby.logOut(playerID, lobbyID);
    }
}
