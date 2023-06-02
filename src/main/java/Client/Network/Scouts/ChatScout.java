package Client.Network.Scouts;

import static Client.ClientApp.view;
import Interface.Scout;
import Utils.ChatMessage;

public class ChatScout implements Scout<ChatMessage>{
    @Override
    public void update(ChatMessage object) {
        view.updateChat(object);
    }
}
