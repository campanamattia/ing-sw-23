package Client.Network.Scouts;

import Interface.Scout;
import Utils.MockObjects.MockPlayer;
import static Client.ClientApp.view;

import java.rmi.RemoteException;

public class PlayerScout implements Scout<MockPlayer> {
    @Override
    public void update(MockPlayer objects) throws RemoteException {
        view.updatePlayer(objects);
    }
}
