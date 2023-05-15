package Server.Network.Lobby;

import Interface.Server.LobbyInterface;
import Interface.Client.RemoteView;
import Server.Controller.GameController;
import Server.Network.Client.ClientHandler;
import Server.Network.Client.RMIHandler;
import Server.Network.Client.SocketHandler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class Lobby extends UnicastRemoteObject implements LobbyInterface {
    private final HashMap<
        String /*lobby ID*/,
        HashMap<
            String /*Player ID*/,
            ClientHandler /*Player Reference*/
        >
    > lobby;
    private final HashMap<Game, GameController> games;
    private final HashMap<String, Integer> lobbySize;

    public Lobby() throws RemoteException {
        super();
        this.lobby = new HashMap<>();
        this.lobbySize = new HashMap<>();
        this.games = new HashMap<>();
    }


    @Override
    public List<Collection<String>> getLobbyInfo() throws RemoteException {
        List<Collection<String>> lobbyInfo = new ArrayList<>();
        Collection<String> lobbyID = this.lobby.keySet();
        Collection<String> gamesName = new ArrayList<>();
        for(Game game : this.games.keySet()){
            gamesName.add(game.getName());
        }
        lobbyInfo.add(lobbyID);
        lobbyInfo.add(gamesName);
        return lobbyInfo;
    }

    @Override
    public synchronized void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        if(this.lobbySize.get(lobbyID) == null){
            if(sizeValid(lobbySize)) {
                this.lobbySize.put(lobbyID, lobbySize);
                this.lobby.get(lobbyID).get(playerID).outcomeMessage("LobbySize set to " + lobbySize); // TODO: 12/05/2023
            }
            else {
                this.lobby.get(lobbyID).get(playerID).outcomeException(new RuntimeException("Lobby size must be between 2 and 4"));
                this.lobby.get(lobbyID).get(playerID).askLobbySize();
            }
        } else{
            this.lobby.get(lobbyID).get(playerID).outcomeException(new RuntimeException("LobbySize has already been set"));
        }
    }

    private boolean sizeValid(int lobbySize){
        return lobbySize >= 2 && lobbySize <= 4;
    }

    // TODO: 10/05/2023 the playerID has to be unique inside the lobby and i have to logout the right player in the right  lobby
    @Override
    public synchronized void logIn(String playerID, String lobbyID, RemoteView remoteView) throws RemoteException {

    }

    public synchronized void logIn(String playerID, String lobbyID,  SocketHandler socketHandler) throws RemoteException {
        if(wasInGame(playerID, lobbyID)){

        }else if(this.lobby.get(lobbyID) != null){
            if(this.lobby.get(lobbyID).containsKey(playerID)) {
                this.lobby.get(lobbyID).get(playerID).outcomeException(new RuntimeException("PlayerID already taken"));
                this.lobby.get(lobbyID).get(playerID).askPlayerID();
            }else {
                this.lobby.get(lobbyID).put(playerID, socketHandler);
                this.lobby.get(lobbyID).get(playerID).setPlayerID(playerID);
                initGame(lobbyID);
            }
        }else {
            this.lobby.put(lobbyID, new HashMap<>());
            this.lobby.get(lobbyID).put(playerID, socketHandler);
            this.lobby.get(lobbyID).get(playerID).setPlayerID(playerID);
            if(!firstPlayer(lobbyID, socketHandler))
                initGame(lobbyID);
        }
    }

    private boolean wasInGame(String playerID, String gameName) {
        for(Game game : this.games.keySet())
            if(game.getName().equals(gameName))
                if(game.getPlayers().containsKey(playerID))
                    return game.getPlayers().get(playerID);
        return false;
    }

    @Override
    public void ping(String playerID, String lobbyID) throws RemoteException {

    }

    @Override
    public synchronized void logOut(String playerID, String lobbyID) throws RemoteException {
        ClientHandler client = this.lobby.get(lobbyID).remove(playerID);
        if(this.lobby.get(lobbyID).size() == 0){
            this.lobby.remove(lobbyID);
            this.lobbySize.remove(lobbyID);
        }


    }

    private boolean firstPlayer(String lobbyID, ClientHandler client) throws RemoteException {
        if(this.lobbySize.get(lobbyID) != null)
            return false;
        else
            client.askLobbySize();
        return true;
    }

    private void initGame(String lobbyID){
        if(this.lobby.get(lobbyID).size() == this.lobbySize.get(lobbyID)){
            this.games.put(lobbyID, new GameController(this.lobby.get(lobbyID)));
            for(ClientHandler client : this.lobby.get(lobbyID).values())
                client.allGame(this.games.get(lobbyID));
            this.lobby.remove(lobbyID);
            this.lobbySize.remove(lobbyID);
        }
    }
}
