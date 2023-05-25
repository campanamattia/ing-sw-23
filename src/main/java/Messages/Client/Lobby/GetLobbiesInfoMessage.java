package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class GetLobbiesInfoMessage extends ClientMessage {
    @Override
    public void execute(SocketHandler socket) {
        try {
            ServerApp.lobby.getLobbyInfo(socket);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.getMessage());
        }
    }
}
