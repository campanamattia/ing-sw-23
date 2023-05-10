package Messages.Server.Listener;

import Client.View.View;
import Enumeration.MessageType;
import Messages.ServerMessage;
import Utils.ChatMessage;

import java.util.Stack;

public class ChatUpdate extends ServerMessage {
    private  Stack<ChatMessage> flow;

    public ChatUpdate(){
        super();
        this.flow = null;
    }

    @Override
    public void execute(View view) {

    }

    public ChatUpdate(Stack<ChatMessage> flow) {
        this.messageType = MessageType.RETURN;
        this.flow = flow;
    }

    public Stack<ChatMessage> getFlow() {
        return flow;
    }
    public void setFlow(Stack<ChatMessage> flow) {
        this.flow = flow;
    }
}
