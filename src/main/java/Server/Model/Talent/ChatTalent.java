package Server.Model.Talent;

import Interface.Scout.ChatScout;
import Utils.ChatRoom;

public class ChatTalent extends Talent<ChatScout, ChatRoom>{
    @Override
    public void notifyScout(ChatRoom chatRoom) {
        for(ChatScout scout : this.scouts)
            scout.update(chatRoom.getFlow());
    }
}
