package Messages.Server.Listener;

import Client.View.View;
import Messages.ServerMessage;
import Utils.ChatMessage;

import java.rmi.RemoteException;
import java.util.Stack;

public class ChatUpdate extends ServerMessage {
    private final ChatMessage message;

    public ChatUpdate(ChatMessage message) {
        this.message = message;
    }

    @Override
    public void execute(View view) {
        try {
            view.updateChat(this.message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
