package Utils.Scouts;

import Client.View.View;
import Interface.Scout;
import Utils.MockObjects.MockPlayer;

import java.rmi.RemoteException;

public class PlayerScout implements Scout<MockPlayer> {
    View view;

    public PlayerScout(View view) {
        this.view = view;
    }

    @Override
    public void update(MockPlayer objects) throws RemoteException {
        this.view.updatePlayer(objects);
    }
}
