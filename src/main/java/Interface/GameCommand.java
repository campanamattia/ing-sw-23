package Interface;

import java.rmi.Remote;

import Messages.Client.WriteChatMessage;
import Messages.Client.InsertTilesMessage;
import Messages.Client.PingMessage;
import Messages.Client.SelectedTilesMessage;
import Messages.Server.PongMessage;
import Messages.Server.StatusMessage;
import Messages.ServerMessage;

public interface GameCommand extends Remote {

    ServerMessage selectedTiles(SelectedTilesMessage message);

    ServerMessage insertTiles(InsertTilesMessage message);

    ServerMessage writeChat(WriteChatMessage message);

    StatusMessage doScreenShot();

    default ServerMessage ping(PingMessage message){
        return new PongMessage();
    };
}