package Model;

import java.time.LocalDateTime;
import java.util.*;
import java.lang.*;

public class ChatRoom {
    private final Stack<ChatMessage> flow;
    private final ReentrantLock locker = new ReentrantLock();

    public ChatRoom() {
        this.flow = new Stack<ChatMessage>();
    }

    public synchronized void addMessage(String sender, String content, LocalDateTime time){
        this.flow.add(new ChatMessage(sender, content, time));
    }

    public synchronized List<String> getHistory(int last){
        List<String> history = new ArrayList<String>();
        Iterator<ChatMessage> iterator = flow.iterator();
        for(int i=0; i<last && iterator.hasNext(); i++)
            history.add(iterator.next().toString());
        return history;
    }
}
