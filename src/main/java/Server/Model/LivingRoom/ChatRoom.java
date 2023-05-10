package Server.Model.LivingRoom;

import com.google.gson.annotations.Expose;

import java.util.*;
import java.lang.*;

/**
 Represents a chat room where users can send and receive messages.
 */
public class ChatRoom {

    /**
     The Stack of WriteChatMessage objects that represents the flow of messages in the chat room.
     */
    @Expose
    private final Stack<ChatMessage> flow;
    /**
     Constructs a new ChatRoomMessage object with an empty flow of messages.
     */
    public ChatRoom() {
        this.flow = new Stack<ChatMessage>();
    }
    /**
     Adds a new WriteChatMessage object to the flow of messages in the chat room.
     @param message the WriteChatMessage object to add to the flow
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
     Returns the flow of WriteChatMessage objects in the chat room.
     @return the flow of WriteChatMessage objects in the chat room
     */
    public Stack<ChatMessage> getFlow() {
        return flow;
    }
}



