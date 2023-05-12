package Server.Network.Client;

import Interface.Client.RemoteView;

public record ClientHandler(String playerID, String lobbyID, RemoteView remoteView) {

}
