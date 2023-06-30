package Client.Network.Scouts;

import Interface.Scout;
import Utils.MockObjects.MockPlayer;
import static Client.ClientApp.view;

import java.rmi.RemoteException;

/**
 * The PlayerScout class implements the Scout interface for observing changes related to player objects.
 * It updates the client with the changes by invoking the appropriate method to update the player.
 */
public class PlayerScout implements Scout<MockPlayer> {

    /**
     * Updates the client with the changes in the player object.
     *
     * @param objects the updated player object
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void update(MockPlayer objects) throws RemoteException {
        view.updatePlayer(objects);
    }
}
