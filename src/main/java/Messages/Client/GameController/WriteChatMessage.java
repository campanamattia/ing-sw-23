package Messages.Client.GameController;

import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class WriteChatMessage extends ClientMessage {
    private final String text;

    public WriteChatMessage(String playerID, String text){
        this.playerID = playerID;
        this.text = text;
    }

    public void execute(SocketHandler socketHandler) {
        GameController gameController=  socketHandler.getGameController();
        try {
            gameController.writeChat(this.playerID,this.text);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
