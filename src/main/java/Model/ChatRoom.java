package Model;

import java.time.LocalDateTime;
import java.util.*;
import java.lang.*;

public class ChatRoom {
    private final Stack<ChatMessage> flow;

    public ChatRoom() {
        this.flow = new Stack<ChatMessage>();
    }

    public synchronized void addMessage(String sender, String content, LocalDateTime time){
        this.flow.add(new ChatMessage(sender, content, time));
    }

    public synchronized List<String> getHistory(int last){
        List<String> history = new ArrayList<String>();
        for(ChatMessage message : flow) history.add(message.toString());
        return history;
    }
}
