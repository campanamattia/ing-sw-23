package Client.Network.Scouts;

import Interface.Scout;
import static Client.ClientApp.view;
import Utils.MockObjects.MockBoard;

import java.rmi.RemoteException;

/**
 * The BoardScout class implements the Scout interface for observing changes related to the game board.
 * It updates the view with the changes by invoking the appropriate method to update the board.
 */
public class BoardScout implements Scout<MockBoard> {

    /**
     * Updates the view with the game board object.
     *
     * @param objects the game board object
     * @throws RemoteException if a remote exception occurs during the update
     */
    @Override
    public void update(MockBoard objects) throws RemoteException {
        view.updateBoard(objects);
    }
}
