package Server.Network.Client;

import Interface.Client.RemoteView;

/**
 * This class it's used to have the complete abstraction between different protocols.
 * @param playerID the playerID
 * @param lobbyID the lobbyID
 * @param remoteView the remoteView interface
 */
public record ClientHandler(String playerID, String lobbyID, RemoteView remoteView) {

}
