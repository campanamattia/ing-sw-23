package Messages.Server.View;

import Client.View.View;
import Messages.ServerMessage;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class AskPlayerInfoMessage extends ServerMessage {
    private final List<Map<String, String>> lobbyInfo;

    public AskPlayerInfoMessage(List<Map<String, String>> lobbyInfo) {
        this.lobbyInfo = lobbyInfo;
    }

    @Override
    public void execute(View view) {
        try {
            view.askPlayerInfo(this.lobbyInfo);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
