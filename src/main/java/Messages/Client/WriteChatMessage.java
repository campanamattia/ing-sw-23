package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.Network.Client.SocketHandler;

public class WriteChatMessage extends ClientMessage {
    private String text;

    public WriteChatMessage(){
        super();
        this.text = null;
    }

    public WriteChatMessage(String playerID, String text){
        this.operationType = OperationType.WRITEMESSAGE;
        this.playerID = playerID;
        this.text = text;
    }


    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public void execute(SocketHandler socketHandler) {
        GameController gameController=  socketHandler.getGameController();
        gameController.writeMessage(this.operationType,this.playerID,this.text);
    }
}
