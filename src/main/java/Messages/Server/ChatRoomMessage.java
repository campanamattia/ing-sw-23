package Messages.Server;

import Enumeration.MessageType;
import Messages.ServerMessage;
import Server.Model.ChatMessage;

import java.util.Stack;

public class ChatRoomMessage extends ServerMessage {
    private  Stack<ChatMessage> flow;

    public ChatRoomMessage(){
        super();
        this.flow = null;
    }

    public ChatRoomMessage(Stack<ChatMessage> flow) {
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
