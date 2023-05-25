package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class GetGameMessage extends ClientMessage {
    private final String lobbyID;

    public GetGameMessage(String lobbyID) {
        super();
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute(SocketHandler socket) {
        try {
            ServerApp.lobby.getGameController(lobbyID, socket);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.getMessage());
        }
    }
}
