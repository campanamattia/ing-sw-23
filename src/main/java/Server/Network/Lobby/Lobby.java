package Server.Network.Lobby;

import Interface.Client.RemoteClient;
import Interface.Server.LobbyInterface;
import Interface.Client.RemoteView;
import Server.Controller.GameController;
import Server.Network.Client.ClientHandler;
import Server.ServerApp;
import Utils.MockObjects.MockFactory;
import Utils.MockObjects.MockModel;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static Server.ServerApp.executorService;
import static Server.ServerApp.logger;

/**
 * The Lobby class represents the lobby system in the game server.
 * It manages player login, logout, lobby sizes, game initialization, and provides lobby information.
 */
public class Lobby extends UnicastRemoteObject implements LobbyInterface {
    /**
     * A mapping of lobby IDs to a mapping of player IDs to their respective client handlers.
     */
    private final HashMap<String, HashMap<String, ClientHandler>> lobby;
    /**
     * A list of active games.
     */
    private final List<GameController> games;
    /**
     * A mapping of player-hashcode to their heartbeat timer.
     */
    private final HashMap<Integer, PingTimer> heartbeat;
    /**
     * A mapping of lobby IDs to their respective lobby sizes.
     */
    private final HashMap<String, Integer> lobbySize;

    /**
     * Constructs a new instance of the Lobby class.
     * Initializes the lobby, heartbeat, lobby size, and games.
     *
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    public Lobby() throws RemoteException {
        super();
        this.heartbeat = new HashMap<>();
        this.lobby = new HashMap<>();
        this.lobbySize = new HashMap<>();
        this.games = new ArrayList<>();
    }

    /**
     * Sends the lobby information to the specified remote view.
     *
     * @param remote the remote view to which the lobby information will be sent
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    @Override
    public synchronized void getLobbyInfo(RemoteView remote) throws RemoteException {
        askPlayerInfo(remote);
    }

    /**
     * Retrieves the lobby information as a list of maps containing lobby and game details.
     *
     * @return a list of maps representing the lobby information. Each map contains lobby ID and player count, and game name and active player count.
     */
    public List<Map<String, String>> getLobbyInfo() {
        if (!this.lobby.isEmpty() || !this.games.isEmpty()) {
            List<Map<String, String>> lobbyInfo = new ArrayList<>();
            Map<String, String> lobbies = new HashMap<>();
            Map<String, String> games = new HashMap<>();
            lobbyInfo.add(lobbies);
            lobbyInfo.add(games);

            // Retrieve lobby information
            for (String lobbyID : this.lobby.keySet()) {
                lobbies.put(lobbyID, this.lobby.get(lobbyID).size() + "/" + this.lobbySize.get(lobbyID));
            }

            // Retrieve game information
            for (GameController game : this.games) {
                games.put(game.getGameID(), game.activePlayers().size() + "/" + game.getPlayers().size());
            }

            return lobbyInfo;
        } else return null;
    }

    /**
     * Handles the login process for a player.
     *
     * @param playerID the ID of the player logging in
     * @param lobbyID  the ID of the lobby in which the player is logging in
     * @param client   the remote view of the player
     * @param network  the remote client representing the player's network connection
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    public synchronized void login(String playerID, String lobbyID, RemoteView client, RemoteClient network) throws RemoteException {
        GameController game = findGame(lobbyID);
        if (game != null)
            rejoinGame(playerID, lobbyID, client, network, game);
        else
            logInLobby(playerID, lobbyID, client, network);
    }

    public GameController findGame(String gameID) {
        return this.games.stream().filter(game -> game.getGameID().equals(gameID)).findFirst().orElse(null);
    }

    private void rejoinGame(String playerID, String lobbyID, RemoteView client, RemoteClient network, GameController gameController) {
        if (!gameController.getPlayers().containsKey(playerID)){
            sendException(client, "Player is not in the game");
            askPlayerInfo(client);
            return;
        }
        if (gameController.getPlayers().get(playerID) != null){
            sendException(client, "Player is already playing");
            askPlayerInfo(client);
            return;
        }

        try {
            gameController.rejoin(playerID, new ClientHandler(playerID, lobbyID, client));
            client.outcomeLogin(playerID, lobbyID);
            client.allGame(MockFactory.getMock(gameController.getGameModel()).clone());
            startTimer(playerID, lobbyID, network);
            network.setGameController(gameController);
            logger.info(lobbyID + " re-registered player: " + playerID);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void logInLobby(String playerID, String lobbyID, RemoteView client, RemoteClient network) throws RemoteException {
        if (!this.lobby.containsKey(lobbyID)){
            createLobby(lobbyID, playerID, client, network);
            return;
        }
        if (this.lobby.get(lobbyID).containsKey(playerID)){
            sendException(client, "PlayerID already taken");
            askPlayerInfo(client);
            return;
        }

        this.lobby.get(lobbyID).put(playerID, new ClientHandler(playerID, lobbyID, client));
        logger.info(lobbyID + " registered new player: " + playerID);
        executorService.execute(() -> {
            try {
                client.outcomeLogin(playerID, lobbyID);
            } catch (RemoteException e) {
                ServerApp.logger.severe(e.getMessage());
            }
        });
        startTimer(playerID, lobbyID, network);
        startGame(lobbyID);
    }

    private void createLobby(String lobbyID, String playerID, RemoteView client, RemoteClient network) throws RemoteException {
        this.lobby.put(lobbyID, new HashMap<>());
        this.lobby.get(lobbyID).put(playerID, new ClientHandler(playerID, lobbyID, client));
        executorService.execute(() -> {
            try {
                client.outcomeLogin(playerID, lobbyID);
                logger.info(playerID + " created new lobby called: " + lobbyID);
            } catch (RemoteException e) {
                logger.severe(e.getMessage());
            }
        });
        startTimer(playerID, lobbyID, network);
        firstPlayer(lobbyID, client);
    }

    private void firstPlayer(String lobbyID, RemoteView client) throws RemoteException {
        if (this.lobbySize.get(lobbyID) == null) {
            executorService.submit(() -> {
                try {
                    client.askLobbySize();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void startTimer(String playerID, String lobbyID, RemoteClient client) {
        int hash = Objects.hash(playerID, lobbyID);
        this.heartbeat.put(hash, new PingTimer(playerID, lobbyID, client));
        this.heartbeat.get(hash).start();
    }

    /**
     * Sets the lobby size for the specified lobby.
     *
     * @param playerID  the ID of the player requesting the lobby size change
     * @param lobbyID   the ID of the lobby for which to set the size
     * @param lobbySize the desired lobby size to set
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    @Override
    public synchronized void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        if (!this.lobby.containsKey(lobbyID)) {
            logger.severe("Lobby " + lobbyID + " does not exist");
            return;
        }
        if (this.lobbySize.get(lobbyID) != null) {
            sendException(this.lobby.get(lobbyID).get(playerID).remoteView(), "Lobby size already set");
            return;
        }
        if (this.lobby.get(lobbyID).size() > lobbySize) {
            sendException(this.lobby.get(lobbyID).get(playerID).remoteView(), "Lobby size must be greater than the number of players already in the lobby");
            askLobbySize(this.lobby.get(lobbyID).get(playerID).remoteView());
            return;
        }
        if (!sizeValid(lobbySize)) {
            sendException(this.lobby.get(lobbyID).get(playerID).remoteView(), "Lobby size must be between 2 and 4");
            askLobbySize(this.lobby.get(lobbyID).get(playerID).remoteView());
            return;
        }

        this.lobbySize.put(lobbyID, lobbySize);
        logger.info("Setting lobby-size to " + lobbySize + "\tfor lobby: " + lobbyID);
        startGame(lobbyID);
    }

    private boolean sizeValid(int lobbySize) {
        return lobbySize >= 2 && lobbySize <= 4;
    }

    private void askLobbySize(RemoteView client){
        executorService.submit(() -> {
            try {
                client.askLobbySize();
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    /**
     * Receives a ping message from the specified player in the given lobby, indicating that the player is still active.
     *
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    @Override
    public synchronized void ping(String playerID, String lobbyID) throws RemoteException {
        int hash = Objects.hash(playerID, lobbyID);
        executorService.execute(() -> {
            try {
                this.heartbeat.get(hash).receivedPing();
            } catch (Exception e) {
                ServerApp.logger.severe(e.getMessage());
            }
        });
    }


    /**
     * Logs out the specified player from the lobby.
     *
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    @Override
    public synchronized void logOut(String playerID, String lobbyID) throws RemoteException {
        ServerApp.logger.info("Logout for " + playerID + "\tin " + lobbyID);

        GameController game = findGame(lobbyID);
        if (game != null) {
            try {
                game.logOut(playerID);
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
            return;
        }

        if (this.lobby.containsKey(lobbyID)) {
            this.lobby.get(lobbyID).remove(playerID);
            if (this.lobby.get(lobbyID).isEmpty()) {
                this.lobby.remove(lobbyID);
                this.lobbySize.remove(lobbyID);
            }
            return;
        }

        logger.severe("Can't find the lobby to log out");
        deleteTimer(playerID, lobbyID);
    }

    private void deleteTimer(String playerID, String lobbyID) {
        int hash = Objects.hash(playerID, lobbyID);
        PingTimer interrupter = this.heartbeat.remove(hash);
        if (interrupter != null) interrupter.interrupt();
    }

    private void startGame(String lobbyID) {
        if (!(this.lobby.get(lobbyID).size() == this.lobbySize.get(lobbyID)) && !(this.lobby.get(lobbyID).size() == 4))
            return;

        GameController game;
        try {
            game = new GameController(lobbyID, this.lobby.get(lobbyID));
        } catch (RemoteException e) {
            logger.severe("Error creating game");
            return;
        }

        this.games.add(game);
        sendGame(game);
        this.lobby.remove(lobbyID);
        this.lobbySize.remove(lobbyID);
        logger.info("Game started for lobby " + lobbyID + " with [" + game.activePlayers().stream()
                .map(ClientHandler::playerID)
                .collect(Collectors.joining(", ")) + "]");
    }

    private void sendGame(GameController game){
        executorService.execute(()->{
            MockModel model = MockFactory.getMock(game.getGameModel());
            for (ClientHandler client : game.activePlayers()) {
                try {
                    client.remoteView().allGame(model.clone());
                    this.heartbeat.get(Objects.hash(client.playerID(), game.getGameID())).getClient().setGameController(game);
                } catch (RemoteException e) {
                    logger.severe("Error sending gameController to player");
                }
            }
        });
    }

    /**
     * It can print the status of the lobby
     */
    public void printLobbyStatus() {
        logger.log(Level.CONFIG, "------------------------------------------Lobby status------------------------------------------");
        if (lobby.isEmpty() && games.isEmpty()) {
            logger.info("No active lobbies or games");
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (!lobby.isEmpty()){
            sb.append("Active lobbies:\n");
            for (String lobbyID : lobby.keySet()) {
                sb.append("\t-\t").append(lobbyID).append(" with ").append(lobby.get(lobbyID).size()).append(" players\n\t\t[");
                sb.append(this.lobby.get(lobbyID).keySet()).append("]\n");
            }
            logger.info(sb.toString());
        }
        if (!games.isEmpty()){
            sb = new StringBuilder();
            sb.append("Active games:\n");
            for (GameController game : games) {
                sb.append("\t-\t").append(game.getGameID()).append(" with ").append(game.activePlayers().size()).append(" players\n\t\t[");
                sb.append(game.activePlayers().stream().map(ClientHandler::playerID).collect(Collectors.joining(", "))).append("]\n");
            }
            logger.info(sb.toString());
        }
    }

    /**
     * It removes the ended game from the list of games
     * @param game the game that has ended
     */
    public void endGame(GameController game) {
        logger.info("Game " + game.getGameID() + " ended");
        try {
            Thread.sleep(30001);
        } catch (InterruptedException e) {
            logger.severe(e.getMessage());
            Thread.currentThread().interrupt();
        }
        executorService.execute(() ->{
            for (ClientHandler handler : game.activePlayers()) {
                try {
                    logOut(handler.playerID(), game.getGameID());
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            }
        });
        this.games.remove(game);
    }

    private void sendException(RemoteView client, String message) {
        executorService.execute(()-> {
            try{
                client.outcomeException(new RuntimeException(message));
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    private void askPlayerInfo(RemoteView client) {
        executorService.execute(()-> {
            try{
                client.askPlayerInfo(getLobbyInfo());
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }
}