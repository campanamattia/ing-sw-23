package Messages.Client.GameController;

import Messages.ClientMessage;
import Server.Network.Client.SocketHandler;
import Server.ServerApp;

import java.rmi.RemoteException;

public class RegisterScout extends ClientMessage {
    private String username;

    public RegisterScout(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void execute(SocketHandler socket) {
        try {
            socket.getGameController().addSubscriber(socket);
        } catch (RemoteException e) {
            ServerApp.logger.severe(e.getMessage());
        }
    }
}
