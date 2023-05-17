package Server.Model.Talent;

import Interface.Scout.ChatScout;
import Utils.ChatMessage;
import Utils.ChatRoom;

import java.rmi.RemoteException;

public class ChatTalent extends Talent<ChatScout, ChatMessage>{
    @Override
    public void notifyScouts(ChatMessage chatMessage) {
        for(ChatScout scout : this.scouts) {
            scout.update(chatMessage);
        }
    }
}
