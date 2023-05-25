package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class LogOutMessage extends ClientMessage {
    private final String lobbyID;
    public LogOutMessage(String playerID, String lobbyID) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
    }

    @Override
    public void execute(SocketHandler socket) {
        try {
            ServerApp.lobby.logOut(playerID, lobbyID);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
