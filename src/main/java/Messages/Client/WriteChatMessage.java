package Messages.Client;

import Enumeration.OperationType;
import Messages.ClientMessage;

public class WriteChatMessage extends ClientMessage {
    private String text;

    public WriteChatMessage(){
        super();
        this.text = null;
    }

    public WriteChatMessage(String playerID, String text){
        this.operationType = OperationType.MESSAGE;
        this.playerID = playerID;
        this.text = text;
    }


    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
