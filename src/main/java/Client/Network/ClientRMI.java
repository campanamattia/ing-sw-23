package Client.Network;

import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Interface.Scout;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Utils.Coordinates;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import static Client.ClientApp.*;

/**
 * The {@code ClientRMI} class represents a client's network implementation using RMI for client-server communication.
 * It extends the {@code Network} class and provides methods to initialize the RMI connection, send messages to the server,
 * and handle incoming messages from the server.
 */
public class ClientRMI extends Network {
    private GameCommand gc;
    private LobbyInterface lobby;

    /**
     * Constructs a new instance of the {@code ClientRMI} class.
     *
     * @throws RemoteException if a remote communication error occurs
     */
    public ClientRMI() throws RemoteException {
        super();
    }

    /**
     * Initializes the RMI connection and retrieves the lobby information from the server.
     */
    @Override
    public void init() {
        try {
            Registry registry = LocateRegistry.getRegistry(IP_SERVER, RMI_PORT);
            this.lobby = (LobbyInterface) registry.lookup("Lobby");
            this.lobby.getLobbyInfo(view);
        } catch (Exception e) {
            try {
                view.outcomeException(e);
            } catch (RemoteException ex) {
                System.exit(404);
            }
        }
    }

    /**
     * Selects tiles for a player.
     *
     * @param playerID    the ID of the player
     * @param coordinates the list of coordinates representing the selected tiles
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.gc.selectTiles(playerID, coordinates);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Writes a chat message.
     * @param from   the ID of the sender
     * @param message the chat message
     * @param to     the ID of the recipient (optional)
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void writeChat(String from, String message, String to) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.gc.writeChat(from, message, to);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Adds a scout for a player.
     *
     * @param playerID the ID of the player
     * @param scout    the scout object
     * @throws RemoteException if a remote communication error occurs
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void addScout(String playerID, Scout scout) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.gc.addScout(localPlayer, this);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Sends a message to insert tiles into a specific column.
     *
     * @param playerID the ID of the player
     * @param sorted   the list of tile sorting indexes
     * @param column   the column number
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void insertTiles(String playerID, List<Integer> sorted, int column) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.gc.insertTiles(playerID, sorted, column);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }


    /**
     * Sends a request to the server to get the lobby information.
     *
     * @param remote the remote view to update with the lobby information
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void getLobbyInfo(RemoteView remote) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.getLobbyInfo(remote);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Sets the size of the lobby.
     *
     * @param playerID  the ID of the player
     * @param lobbyID   the ID of the lobby
     * @param lobbySize the size of the lobby
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void setLobbySize(String playerID, String lobbyID, int lobbySize) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.setLobbySize(playerID, lobbyID, lobbySize);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Logs in a player to the lobby.
     *
     * @param playerID    the ID of the player
     * @param lobbyID     the ID of the lobby
     * @param remoteView  the remote view associated with the player
     * @param client      the remote client network interface
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void login(String playerID, String lobbyID, RemoteView remoteView, RemoteClient client) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.login(playerID, lobbyID, remoteView, client);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Sends a ping message to the server.
     *
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void ping(String playerID, String lobbyID) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.ping(playerID, lobbyID);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Logs out the player from the lobby.
     *
     * @param playerID the ID of the player
     * @param lobbyID  the ID of the lobby
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void logOut(String playerID, String lobbyID) throws RemoteException {
        executorService.execute(() -> {
            try {
                this.lobby.logOut(playerID, lobbyID);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Sets the game controller for the client.
     *
     * @param gameController the game controller object
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void setGameController(GameCommand gameController) throws RemoteException {
        this.gc = gameController;
        executorService.execute(() -> {
            try {
                this.gc.addScout(localPlayer, this);
            } catch (RemoteException e) {
                System.exit(404);
            }
        });
    }

    /**
     * Updates the client with the received objects.
     *
     * @param objects the objects to update the client with
     * @throws RemoteException if a remote communication error occurs
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(Object objects) throws RemoteException {
        if (scouts.containsKey(objects.getClass())) {
            scouts.get(objects.getClass()).update(objects);
        } else {
            view.outcomeException(new RuntimeException("Scout-Handler not found"));
            throw new RemoteException("Scout not found");
        }
    }
}
