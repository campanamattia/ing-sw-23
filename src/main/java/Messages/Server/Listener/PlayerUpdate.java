package Messages.Server.Listener;

import Client.View.View;
import Messages.ServerMessage;
import Utils.MockObjects.MockPlayer;

public class PlayerUpdate extends ServerMessage {
    private final MockPlayer mockPlayer;

    public PlayerUpdate(MockPlayer mockPlayer) {
        this.mockPlayer = mockPlayer;
    }

    @Override
    public void execute(View view) {
        view.updatePlayer(this.mockPlayer);
    }
}
