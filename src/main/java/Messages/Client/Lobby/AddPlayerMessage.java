package Messages.Client.Lobby;

import Interface.Client.RemoteClient;
import Interface.Client.RemoteView;
import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class AddPlayerMessage extends ClientMessage {
    private final String lobbyID;

    public AddPlayerMessage(String playerID, String lobbyID) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    public void execute(SocketHandler socketHandler){
        try {
            ServerApp.lobby.login(this.playerID,this.lobbyID, socketHandler, socketHandler);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
