package Server.Network.Lobby;

import Interface.Client.RemoteClient;
import Interface.Server.LobbyInterface;
import Interface.Client.RemoteView;
import Server.Controller.GameController;
import Server.Network.Client.ClientHandler;
import Server.ServerApp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class Lobby extends UnicastRemoteObject implements LobbyInterface {
    private final HashMap<String /*lobby ID*/, HashMap<String /*Player ID*/, ClientHandler /*Player Reference*/>> lobby;
    private final HashMap<Game, GameController> games;
    private final HashMap<Integer, PingTimer> heartbeat;
    private final HashMap<String, Integer> lobbySize;

    public Lobby() throws RemoteException {
        super();
        this.heartbeat = new HashMap<>();
        this.lobby = new HashMap<>();
        this.lobbySize = new HashMap<>();
        this.games = new HashMap<>();
    }


    @Override
    public void getLobbyInfo(RemoteView remote) throws RemoteException {
        List<Collection<String>> lobbyInfo = new ArrayList<>();
        Collection<String> lobbyID = this.lobby.keySet();
        Collection<String> gamesName = new ArrayList<>();
        for (Game game : this.games.keySet()) {
            gamesName.add(game.name());
        }
        lobbyInfo.add(lobbyID);
        lobbyInfo.add(gamesName);
        remote.askPlayerInfo(lobbyInfo);
    }

    @Override
    public synchronized void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        if (this.lobbySize.get(lobbyID) == null) {
            if (sizeValid(lobbySize)) {
                this.lobbySize.put(lobbyID, lobbySize);
                this.lobby.get(lobbyID).get(playerID).remoteView().outcomeMessage("LobbySize set to " + lobbySize); // TODO: 12/05/2023
            } else {
                this.lobby.get(lobbyID).get(playerID).remoteView().outcomeException(new RuntimeException("Lobby size must be between 2 and 4"));
                this.lobby.get(lobbyID).get(playerID).remoteView().askLobbySize();
            }
        } else {
            this.lobby.get(lobbyID).get(playerID).remoteView().outcomeException(new RuntimeException("LobbySize has already been set"));
        }
    }

    private boolean sizeValid(int lobbySize) {
        return lobbySize >= 2 && lobbySize <= 4;
    }

    public synchronized void logIn(String playerID, String lobbyID, RemoteView client, RemoteClient network) throws RemoteException {
        Game game = findGame(lobbyID);
        if (game != null) { //if the game exists
            if (game.players().containsKey(playerID)) {
                if (!game.players().get(playerID)) { //if the player is not playing
                    game.setStatus(playerID, true);
                    reload(playerID, lobbyID, client);
                    startTimer(playerID, lobbyID, network);
                } else { //if the player is playing
                    client.outcomeException(new RuntimeException("Player is already playing"));
                    client.askPlayerID();
                }
            }
        } else if (this.lobby.get(lobbyID) != null) { //if the lobby exists
            if (this.lobby.get(lobbyID).containsKey(playerID)) { //if the playerID is already taken
                this.lobby.get(lobbyID).get(playerID).remoteView().outcomeException(new RuntimeException("PlayerID already taken"));
                this.lobby.get(lobbyID).get(playerID).remoteView().askPlayerID();
            } else { //if the playerID is not taken
                this.lobby.get(lobbyID).put(playerID, new ClientHandler(playerID, lobbyID, client));
                startTimer(playerID, lobbyID, network);
                initGame(lobbyID);
            }
        } else { //if the lobby does not exist
            this.lobby.put(lobbyID, new HashMap<>());
            this.lobby.get(lobbyID).put(playerID, new ClientHandler(playerID, lobbyID, client));
            startTimer(playerID, lobbyID, network);
            if (!firstPlayer(lobbyID, client))
                initGame(lobbyID);
        }
    }

    private void startTimer(String playerID, String lobbyID, RemoteClient client) {
        int hash =  Objects.hash(playerID, lobbyID);
        this.heartbeat.put(hash, new PingTimer(playerID, lobbyID, client));
        this.heartbeat.get(hash).start();
    }

    @Override
    public synchronized void ping(String playerID, String lobbyID) throws RemoteException {
        int hash =  Objects.hash(playerID, lobbyID);
        this.heartbeat.get(hash).ping();
    }

    @Override
    public void getGameController(String lobbyID, RemoteClient remote) throws RemoteException {
        Game game = findGame(lobbyID);
        if (game != null) {
            remote.setGameController(this.games.get(game));
        } else {
            ServerApp.logger.severe("Game not found");
        }
    }

    private Game findGame(String lobbyID) {
        return this.games.keySet().stream()
                .filter(game -> game.name().equals(lobbyID))
                .findFirst()
                .orElse(null);
    }


    @Override // TODO: 12/05/2023 da rifare assolutamente 
    public synchronized void logOut(String playerID, String lobbyID) throws RemoteException {
        ClientHandler client = this.lobby.get(lobbyID).remove(playerID);
        if (this.lobby.get(lobbyID).size() == 0) {
            this.lobby.remove(lobbyID);
            this.lobbySize.remove(lobbyID);
        }
        
        deleteTimer(playerID, lobbyID);
    }

    private void deleteTimer(String playerID, String lobbyID) {
        int hash =  Objects.hash(playerID, lobbyID);
        this.heartbeat.get(hash).interrupt();
        this.heartbeat.remove(hash);
    }

    private boolean firstPlayer(String lobbyID, RemoteView client) throws RemoteException {
        if (this.lobbySize.get(lobbyID) == null) {
            client.askLobbySize();
            return true;
        }
        return false;
    }

    private void initGame(String lobbyID) {
        if (this.lobby.get(lobbyID).size() == this.lobbySize.get(lobbyID)) {
            HashMap<String, Boolean> players = new HashMap<>();
            for (String playerID : this.lobby.get(lobbyID).keySet())
                players.put(playerID, true);
            this.games.put(new Game(lobbyID, players), new GameController(this.lobby.get(lobbyID)));
            for (ClientHandler client : this.lobby.get(lobbyID).values())
                client.remoteView().allGame(findGame(lobbyID));
            this.lobby.remove(lobbyID);
            this.lobbySize.remove(lobbyID);
        }
    }

    private void reload(String playerID, String lobbyID, RemoteView remoteView) {
        GameController gameController = this.games.get(findGame(lobbyID));
        gameController.reload(playerID, remoteView);
    }

}
