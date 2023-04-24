package Interface;

import Messages.Client.AddPlayerMessage;
import Messages.ServerMessage;

import java.rmi.Remote;

public interface PreGameCommand  extends Remote {

    ServerMessage addPlayer(AddPlayerMessage message);

}
