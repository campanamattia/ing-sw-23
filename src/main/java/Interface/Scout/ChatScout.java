package Interface.Scout;

import Utils.ChatMessage;

import java.util.Stack;

public interface ChatScout extends Scout{
    void update(Stack<ChatMessage> flow);
}