package Messages.Server.Listener;

import Client.View.View;
import Messages.ServerMessage;
import Utils.ChatMessage;

import java.util.Stack;

public class ChatUpdate extends ServerMessage {
    private final Stack<ChatMessage> flow;

    public ChatUpdate(Stack<ChatMessage> flow) {
        this.flow = flow;
    }

    @Override
    public void execute(View view) {
        view.updateChat(this.flow);
    }
}
