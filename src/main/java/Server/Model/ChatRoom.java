package Server.Model;

import com.google.gson.annotations.Expose;

import java.util.*;
import java.lang.*;

/**
 Represents a chat room where users can send and receive messages.
 */
public class ChatRoom {

    /**
     The Stack of ChatMessage objects that represents the flow of messages in the chat room.
     */
    @Expose
    private final Stack<ChatMessage> flow;
    /**
     Constructs a new ChatRoom object with an empty flow of messages.
     */
    public ChatRoom() {
        this.flow = new Stack<ChatMessage>();
    }
    /**
     Adds a new ChatMessage object to the flow of messages in the chat room.
     @param message the ChatMessage object to add to the flow
     */
    public synchronized void addMessage(ChatMessage message){
        this.flow.add(message);
    }
    /**
     Returns a list of the last messages in the chat room's flow, represented as strings.
     @param last the number of messages to retrieve from the chat room's flow
     @return a list of the last messages in the chat room's flow, represented as strings
     */
    public synchronized List<String> getHistory(int last){
        List<String> history = new ArrayList<String>();
        for(ChatMessage message : flow) {
            history.add(message.toString());
            if(history.size() == last) break;
        }
        return history;
    }
    /**
     Returns the flow of ChatMessage objects in the chat room.
     @return the flow of ChatMessage objects in the chat room
     */
    public Stack<ChatMessage> getFlow() {
        return flow;
    }
}



