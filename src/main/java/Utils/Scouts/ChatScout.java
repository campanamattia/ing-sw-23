package Utils.Scouts;

import Client.View.View;
import Interface.Scout;
import Utils.ChatMessage;

public class ChatScout implements Scout<ChatMessage>{
    View view;

    public ChatScout(View view) {
        this.view = view;
    }

    @Override
    public void update(ChatMessage object) {
        this.view.updateChat(object);
    }
}
