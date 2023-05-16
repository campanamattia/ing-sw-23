package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class PingMessage extends ClientMessage {

    private final String lobbyID;
    public PingMessage(String playerID,String lobbyID){
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    public void execute(SocketHandler socketHandler){
        try {
            ServerApp.lobby.ping(this.playerID,this.lobbyID);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
