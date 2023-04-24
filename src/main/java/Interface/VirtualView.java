package Interface;

import java.rmi.Remote;
import Messages.ServerMessage;

public interface VirtualView extends Remote {

    void newTurn(ServerMessage message);
    void EndTurn(ServerMessage message);

}
