package Messages.Client;

import Client.View.Cli;
import Enumeration.OperationType;
import Messages.ClientMessage;

public class FirstPlayerMessage extends ClientMessage {
    Boolean firstPlayer;

    public FirstPlayerMessage(String playerID) {
        this.operationType = OperationType.FIRSTPLAYER;
        this.playerID = playerID;
        this.firstPlayer = true;
    }

    public Boolean getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
}
