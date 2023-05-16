package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;
import Server.Controller.GameController;
import Server.ServerApp;
public class AddPlayerMessage extends ClientMessage {

    public AddPlayerMessage(String playerID) {
        this.operationType = OperationType.ADDPLAYER;
        this.playerID = playerID;
    }

    public void execute(GameController gameController){
        ServerApp.lobby.addPlayer(this.operationType,this.playerID);
    }
}
