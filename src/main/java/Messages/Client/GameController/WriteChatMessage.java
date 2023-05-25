package Messages.Client.GameController;

import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class WriteChatMessage extends ClientMessage {
    private final String message;
    private final String to;

    public WriteChatMessage(String from, String message, String to){
        this.playerID = from;
        this.message = message;
        this.to = to;
    }

    public void execute(SocketHandler socketHandler) {
        GameController gameController=  socketHandler.getGameController();
        try {
            gameController.writeChat(this.playerID, this.message, this.to);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
