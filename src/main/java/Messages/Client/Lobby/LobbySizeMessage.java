package Messages.Client.Lobby;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class LobbySizeMessage extends ClientMessage {
    private final String lobbyID;
    private final int lobbySize;

    public LobbySizeMessage(String playerID, String lobbyID, int lobbySize) {
        this.playerID = playerID;
        this.lobbyID = lobbyID;
        this.lobbySize = lobbySize;
    }

    @Override
    public void execute(SocketHandler socket) {
        try {
            ServerApp.lobby.setLobbySize(this.playerID, this.lobbyID, this.lobbySize);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.toString());
        }
    }
}
