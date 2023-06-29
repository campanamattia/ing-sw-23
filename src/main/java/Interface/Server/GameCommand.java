package Interface.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Interface.Scout;
import Utils.Coordinates;

/**
 * The GameCommand interface represents a remote interface for player commands.
 * It defines methods for selecting tiles, inserting tiles, writing chat messages,
 * and adding a scout to the game.
 */
public interface GameCommand extends Remote {

    /**
     * Selects tiles by a player with the specified player ID.
     *
     * @param playerID    the ID of the player.
     * @param coordinates the list of coordinates representing the selected tiles.
     * @throws RemoteException if a remote communication error occurs.
     */
    void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException;

    /**
     * Inserts tiles by a player with the specified player ID into the specified column.
     *
     * @param playerID the ID of the player.
     * @param sort the list of integers representing the sorted tiles to insert.
     * @param column the column where the tiles should be inserted.
     * @throws RemoteException if a remote communication error occurs.
     */
    void insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException;

    /**
     * Writes a chat message from the specified sender to the specified recipient otherwise to all.
     *
     * @param from the ID of the sender.
     * @param message the chat message to send.
     * @param to the ID of the recipient.
     * @throws RemoteException if a remote communication error occurs.
     */
    void writeChat(String from, String message, String to) throws RemoteException;

    /**
     * Adds a scout with the specified player ID to the game.
     *
     * @param playerID the ID of the scout player.
     * @param scout    the scout object to add.
     * @throws RemoteException if a remote communication error occurs.
     */
    void addScout(String playerID, Scout scout) throws RemoteException;
}