package Messages.Client.GameController;

import Interface.Server.GameCommand;
import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;
import Utils.Coordinates;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Represents a client message used to indicate the selected tiles by a player in the game.
 * It is sent by a client to the server to notify the selected tiles.
 */
public class SelectedTilesMessage extends ClientMessage {
    private final List<Coordinates> coordinates;

    /**
     * Constructs a SelectedTilesMessage with the specified player ID and selected tile coordinates.
     *
     * @param playerID    the ID of the player selecting the tiles.
     * @param coordinates the list of coordinates representing the selected tiles.
     */
    public SelectedTilesMessage(String playerID, List<Coordinates> coordinates){
        this.playerID = playerID;
        this.coordinates = coordinates;
    }

    /**
     * Executes the client message by invoking the appropriate method on the game controller to handle the selected tiles.
     *
     * @param socketHandler the SocketHandler used for communication with the server.
     */
    public void execute(SocketHandler socketHandler) {
        GameCommand gameController=  socketHandler.getGameController();
        try {
            gameController.selectTiles(this.playerID,this.coordinates);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.getMessage());
        }
    }
}
