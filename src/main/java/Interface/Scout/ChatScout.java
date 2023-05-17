package Interface.Scout;

import Utils.ChatMessage;

import java.rmi.RemoteException;


public interface ChatScout extends Scout<ChatMessage>{
    @Override
    void update(ChatMessage objects) throws RemoteException;
}