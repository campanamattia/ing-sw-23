package Utils;

import Interface.Scout.Scout;
import Server.Model.Talent.ChatTalent;
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
    private final ChatTalent talent;

    /**
     Constructs a new ChatUpdate object with an empty flow of messages.
     */
    public ChatRoom() {
        this.flow = new Stack<ChatMessage>();
        this.talent = new ChatTalent();
    }
    /**
     Adds a new WriteChatMessage object to the flow of messages in the chat room.
     @param message the WriteChatMessage object to add to the flow
     */
    public synchronized void addMessage(ChatMessage message){
        this.flow.add(message);
        this.talent.notifyScouts(message);
    }
    /**
     Returns the flow of WriteChatMessage objects in the chat room.
     @return the flow of WriteChatMessage objects in the chat room
     */
    public Stack<ChatMessage> getFlow() {
        return flow;
    }

    public ChatTalent getTalent() {
        return this.talent;
    }
}