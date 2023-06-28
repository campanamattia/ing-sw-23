package Interface.Client;

import Enumeration.GameWarning;
import Utils.MockObjects.MockModel;
import Utils.Rank;
import Utils.Tile;

import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * This interface defines the remote view for the game client.
 */
public interface RemoteView extends Remote {

    /**
     * Handles the event when a new turn begins.
     *
     * @param currentPlayer The ID of the player starting the new turn.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void newTurn(String currentPlayer) throws RemoteException;

    /**
     * Asks the player to specify the lobby size and sets the lobby size for the game.
     *
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void askLobbySize() throws RemoteException;

    /**
     * Handles the outcome of the tile selection during a turn.
     *
     * @param selectedTiles The list of selected tiles.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void outcomeSelectTiles(List<Tile> selectedTiles) throws RemoteException;

    /**
     * Handles the outcome of the tile insertion during a turn.
     *
     * @param success True if the insertion was successful, false otherwise.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void outcomeInsertTiles(boolean success) throws RemoteException;

    /**
     * Handles an exception during the game.
     *
     * @param e The exception that occurred.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void outcomeException(Exception e) throws RemoteException;

    /**
     * Handles the outcome of the login process.
     *
     * @param localPlayer The ID of the local player.
     * @param lobbyID     The ID of the lobby.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void outcomeLogin(String localPlayer, String lobbyID) throws RemoteException;

    /**
     * Asks the player for lobby information and handles the login process.
     *
     * @param lobbyInfo The list of lobby information.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void askPlayerInfo(List<Map<String, String>> lobbyInfo) throws RemoteException;

    /**
     * Sets up the local machine with the provided MockModel.
     *
     * @param mockModel The MockModel representing the game state.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void allGame(MockModel mockModel) throws RemoteException;

    /**
     * Displays the final leaderboard of the game.
     *
     * @param leaderboard The list of player ranks.
     */
    void endGame(List<Rank> leaderboard) throws RemoteException;

    /**
     * Handles the event when a player crashes.
     *
     * @param crashedPlayer The ID of the crashed player.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void crashedPlayer(String crashedPlayer) throws RemoteException;

    /**
     * Handles the event when a player reconnects.
     *
     * @param reloadPlayer The ID of the player who reconnected.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void reloadPlayer(String reloadPlayer) throws RemoteException;

    /**
     * Handles the outcome message of the game.
     *
     * @param warning The game warning message.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    void outcomeMessage(GameWarning warning) throws RemoteException;
}
