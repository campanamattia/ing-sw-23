package Messages.Client.GameController;

import Interface.Server.GameCommand;
import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

/**
 * Represents a client message used to write a chat message in the game.
 * It is sent by a client to the server to deliver the chat message.
 */
public class WriteChatMessage extends ClientMessage {
    private final String message;
    private final String to;

    /**
     * Constructs a WriteChatMessage with the specified sender, message, and recipient.
     *
     * @param from the ID of the player sending the chat message.
     * @param message the content of the chat message.
     * @param to the ID of the recipient of the chat message.
     */
    public WriteChatMessage(String from, String message, String to){
        this.playerID = from;
        this.message = message;
        this.to = to;
    }

    /**
     * Executes the client message by invoking the appropriate method on the game controller to send the chat message.
     *
     * @param socketHandler the SocketHandler used for communication with the server.
     */
    public void execute(SocketHandler socketHandler) {
        GameCommand gameController=  socketHandler.getGameController();
        try {
            gameController.writeChat(this.playerID, this.message, this.to);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
