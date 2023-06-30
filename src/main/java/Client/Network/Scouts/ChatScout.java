package Client.Network.Scouts;

import static Client.ClientApp.view;
import Interface.Scout;
import Utils.ChatMessage;

/**
 * The ChatScout class implements the Scout interface for observing changes related to chat messages.
 * It updates the view with the changes by invoking the appropriate method to update the chat.
 */
public class ChatScout implements Scout<ChatMessage>{

    /**
     * Updates the view with the chat message object.
     *
     * @param object the chat message object
     */
    @Override
    public void update(ChatMessage object) {
        view.updateChat(object);
    }
}
