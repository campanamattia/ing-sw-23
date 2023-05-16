package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.ServerApp;

public class PingMessage extends ClientMessage {

    public PingMessage(){
        super();
    }

    public PingMessage(String playerID){
        this.operationType = OperationType.PING;
        this.playerID = playerID;
    }

    public void execute(GameController gameController){
        ServerApp.lobby.ping(this.operationType,this.playerID);
    }
}
