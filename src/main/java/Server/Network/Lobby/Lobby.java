package Server.Network.Lobby;

import Interface.Client.RemoteClient;
import Interface.Server.LobbyInterface;
import Interface.Client.RemoteView;
import Server.Controller.GameController;
import Server.Network.Client.ClientHandler;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;
import Utils.MockObjects.MockFactory;
import Utils.MockObjects.MockModel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;

import static Server.ServerApp.executorService;

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
        executorService.submit(() -> {
            try {
                remote.askPlayerInfo(getLobbyInfo());
            } catch (RemoteException e) {
                ServerApp.logger.severe(e.getMessage());
            }
        });
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
                games.put(game.getGameID(), activePlayers(game) + "/" + game.getPlayers().size());
            }

            return lobbyInfo;
        } else return null;
    }

    /**
     * Calculates the number of active players in the specified game.
     *
     * @param game the game for which to count the active players
     * @return the number of active players in the game
     */
    private int activePlayers(GameController game) {
        int activePlayers = 0;
        for (ClientHandler status : game.getClients()) {
            activePlayers += (status != null) ? 1 : 0;
        }
        return activePlayers;
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
        ServerApp.logger.info(playerID + " is trying to login " + lobbyID);
        GameController game = findGame(lobbyID);
        if (game != null)
            rejoinGame(playerID, lobbyID, client, network, game);//if the game exists
        else
            logInLobby(playerID, lobbyID, client, network);
    }

    /**
     * Finds a game with the specified lobby ID.
     *
     * @param gameID the ID of the lobby
     * @return the GameController object corresponding to the lobby ID, or null if no matching game is found
     */
    private GameController findGame(String gameID) {
        return this.games.stream().filter(game -> game.getGameID().equals(gameID)).findFirst().orElse(null);
    }

    /**
     * Attempts to rejoin a disconnected player to the game.
     *
     * @param playerID   The ID of the player who wants to rejoin.
     * @param lobbyID    The ID of the lobby where the player wants to rejoin.
     * @param remoteView The remote view of the player.
     */
    private void rejoinGame(String playerID, String lobbyID, RemoteView remoteView, RemoteClient network, GameController gameController) {
        if (gameController.getPlayers().containsKey(playerID)) {
            if (gameController.getPlayers().get(playerID) == null) {//if the player is not playing
                executorService.execute(() -> {
                    try {
                        gameController.rejoin(playerID, new ClientHandler(playerID, lobbyID, remoteView));
                        remoteView.outcomeLogin(playerID, lobbyID);
                        MockModel model = MockFactory.getMock(gameController.getGameModel());
                        model.setLocalPlayer(playerID);
                        remoteView.allGame(model);
                        startTimer(playerID, lobbyID, network);
                        network.setGameController(gameController);
                    } catch (RemoteException e) {
                        ServerApp.logger.severe("Error rejoining player to game");
                    }
                });
            } else { //if the player is playing
                executorService.submit(() -> {
                    try {
                        remoteView.outcomeException(new RuntimeException("Player is already playing"));
                        remoteView.askPlayerInfo(getLobbyInfo());
                    } catch (RemoteException e) {
                        ServerApp.logger.log(Level.SEVERE, e.getMessage());
                    }
                });
            }
        }
    }

    private void logInLobby(String playerID, String lobbyID, RemoteView client, RemoteClient network) throws RemoteException {
        if (this.lobby.containsKey(lobbyID)) { //if the lobby exists
            if (this.lobby.get(lobbyID).containsKey(playerID)) { //if the playerID is already taken
                executorService.submit(() -> {
                    try {
                        client.outcomeException(new RuntimeException("PlayerID already taken"));
                        client.askPlayerInfo(getLobbyInfo());
                    } catch (RemoteException e) {
                        ServerApp.logger.log(Level.SEVERE, e.getMessage());
                    }
                });
            } else { //if the playerID is not taken
                this.lobby.get(lobbyID).put(playerID, new ClientHandler(playerID, lobbyID, client));
                ServerApp.logger.info(lobbyID + " registered new player: " + playerID);
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
        } else { //if the lobby does not exist
            createLobby(lobbyID, playerID, client, network);
        }
    }

    /**
     * Handles the creation process for a lobby.
     *
     * @param playerID the ID of the player creating the lobby
     * @param lobbyID  the ID of the lobby the player is creating
     * @param client   the remote view of the player
     * @param network  the remote client representing the player's network connection
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    private void createLobby(String lobbyID, String playerID, RemoteView client, RemoteClient network) throws RemoteException {
        this.lobby.put(lobbyID, new HashMap<>());
        this.lobby.get(lobbyID).put(playerID, new ClientHandler(playerID, lobbyID, client));
        executorService.execute(() -> {
            try {
                client.outcomeLogin(playerID, lobbyID);
                ServerApp.logger.info(playerID + " created new lobby called: " + lobbyID);
            } catch (RemoteException e) {
                ServerApp.logger.severe(e.getMessage());
            }
        });
        startTimer(playerID, lobbyID, network);
        if (!firstPlayer(lobbyID, client)) startGame(lobbyID);
    }

    /**
     * Checks if the lobby has no players and prompts the client to provide the lobby size.
     *
     * @param lobbyID the ID of the lobby
     * @param client  the remote view of the client
     * @return true if the lobby size needs to be set, false otherwise
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    private boolean firstPlayer(String lobbyID, RemoteView client) throws RemoteException {
        if (this.lobbySize.get(lobbyID) == null) {
            executorService.submit(() -> {
                try {
                    client.askLobbySize();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * Starts a ping timer for the specified player in the given lobby.
     *
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     * @param client   the remote client representing the player's network connection
     */
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
        if (this.lobby.containsKey(lobbyID)) {
            if (this.lobbySize.get(lobbyID) == null) {
                if (this.lobby.get(lobbyID).size() <= lobbySize) {
                    if (sizeValid(lobbySize)) {
                        this.lobbySize.put(lobbyID, lobbySize);
                        ServerApp.logger.info("Setting lobby-size to " + lobbySize + "\tfor lobby: " + lobbyID);
                        startGame(lobbyID);
                    } else {
                        executorService.submit(() -> {
                            try {
                                this.lobby.get(lobbyID).get(playerID).remoteView().outcomeException(new RuntimeException("Lobby size must be between 2 and 4"));
                                this.lobby.get(lobbyID).get(playerID).remoteView().askLobbySize();
                            } catch (RemoteException e) {
                                ServerApp.logger.log(Level.SEVERE, e.getMessage());
                            }
                        });
                    }
                } else {
                    executorService.submit(() -> {
                        try {
                            this.lobby.get(lobbyID).get(playerID).remoteView().outcomeException(new RuntimeException("Lobby size must be greater than or equal to the number of players in the lobby"));
                            this.lobby.get(lobbyID).get(playerID).remoteView().askLobbySize();
                        } catch (RemoteException e) {
                            ServerApp.logger.log(Level.SEVERE, e.getMessage());
                        }
                    });
                }
            } else {
                executorService.submit(() -> {
                    try {
                        this.lobby.get(lobbyID).get(playerID).remoteView().outcomeException(new RuntimeException("LobbySize has already been set"));
                    } catch (RemoteException e) {
                        ServerApp.logger.log(Level.SEVERE, e.getMessage());
                    }
                });
            }
        } else ServerApp.logger.severe("Can't find the lobby to set it's size");
    }

    /**
     * Checks if the specified lobby size is valid.
     *
     * @param lobbySize the lobby size to validate
     * @return true if the lobby size is between 2 and 4 (inclusive), false otherwise
     */
    private boolean sizeValid(int lobbySize) {
        return lobbySize >= 2 && lobbySize <= 4;
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
     * Retrieves the game controller for the specified lobby and provides it to the remote client.
     *
     * @param lobbyID the ID of the lobby
     * @param remote  the remote client to receive the game controller
     * @throws RemoteException if a communication error occurs during the remote method call
     */
    @Override
    public void getGameController(String lobbyID, RemoteClient remote) throws RemoteException {
        GameController game = findGame(lobbyID);
        if (game != null) {
            executorService.execute(() -> {
                try {
                    remote.setGameController(game);
                } catch (RemoteException e) {
                    ServerApp.logger.log(Level.SEVERE, e.getMessage());
                }
            });
        } else {
            ServerApp.logger.severe("Game not found");
        }
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
        executorService.execute(() -> {
            GameController game = findGame(lobbyID);
            ClientHandler clientHandler;
            if (game != null) {
                try {
                    clientHandler = game.logOut(playerID);
                    if (activePlayers(game) <= 1) {
                        this.games.remove(game);
                        for (ClientHandler handler : game.getClients())
                            handler.remoteView().outcomeException(new Exception("The game was concluded due to insufficient active players."));
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else if (this.lobby.containsKey(lobbyID)) {
                clientHandler = this.lobby.get(lobbyID).remove(playerID);
                if (this.lobby.get(lobbyID).isEmpty()) {
                    this.lobby.remove(lobbyID);
                    this.lobbySize.remove(lobbyID);
                }
            } else throw new RuntimeException("Lobby not found");
            if (clientHandler.remoteView() instanceof SocketHandler)
                ((SocketHandler) clientHandler.remoteView()).logOut();
            deleteTimer(playerID, lobbyID);
            printLobbyStatus();
        });
    }

    /**
     * Deletes the ping timer for the specified player in the given lobby.
     *
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     */
    private void deleteTimer(String playerID, String lobbyID) {
        int hash = Objects.hash(playerID, lobbyID);
        PingTimer interrupter = this.heartbeat.remove(hash);
        if (interrupter != null) interrupter.interrupt();
    }

    /**
     * Initializes a new game for the specified lobby if the lobby is full.
     *
     * @param lobbyID the ID of the lobby
     */
    private void startGame(String lobbyID) {
        if (this.lobby.get(lobbyID).size() == this.lobbySize.get(lobbyID) || this.lobby.get(lobbyID).size() == 4) {
            executorService.execute(() -> {
                try {
                    GameController game = new GameController(lobbyID, this.lobby.get(lobbyID));
                    this.games.add(game);
                    executorService.execute(() -> {
                        MockModel model = MockFactory.getMock(game.getGameModel());
                        for (ClientHandler client : this.lobby.get(lobbyID).values()) {
                            model.setLocalPlayer(client.playerID());
                            try {
                                client.remoteView().allGame(model);
                                this.heartbeat.get(Objects.hash(client.playerID(), lobbyID)).getClient().setGameController(game);
                            } catch (RemoteException e) {
                                ServerApp.logger.severe("Error sending gameController to player");
                            }
                        }
                        this.lobby.remove(lobbyID);
                        this.lobbySize.remove(lobbyID);
                        printLobbyStatus();
                    });
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void printLobbyStatus() {
        StringBuilder print = new StringBuilder("LOBBY STATUS: \n");
        for (String object : this.lobby.keySet())
            print.append("LobbyID: ").append(object).append("\tWaiting Room: ").append(this.lobby.get(object).size()).append("/").append(this.lobbySize.get(object)).append("\n");
        for (GameController object : this.games)
            print.append("GameID: ").append(object.getGameID()).append("\tPlayers Online: ").append(activePlayers(object)).append("/").append(object.getPlayers().size()).append("\t\t").append("GameController: ").append((this.games.contains(object)) ? "on going" : null).append("\n");
        ServerApp.logger.info(print.toString());
    }
}