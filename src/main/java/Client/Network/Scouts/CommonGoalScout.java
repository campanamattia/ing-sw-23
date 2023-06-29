package Client.Network.Scouts;

import Interface.Scout;
import Utils.MockObjects.MockCommonGoal;
import static Client.ClientApp.view;

import java.rmi.RemoteException;

/**
 * The CommonGoalScout class implements the Scout interface for observing changes related to common goals.
 * It updates the client with the changes by invoking the appropriate method to update the common goal.
 */
public class CommonGoalScout implements Scout<MockCommonGoal> {

    /**
     * Updates the view with the changes in the common goal.
     *
     * @param objects the updated common goal object
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public void update(MockCommonGoal objects) throws RemoteException {
        view.updateCommonGoal(objects);
    }
}
