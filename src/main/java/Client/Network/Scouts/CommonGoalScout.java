package Client.Network.Scouts;

import Interface.Scout;
import Utils.MockObjects.MockCommonGoal;
import static Client.ClientApp.view;

import java.rmi.RemoteException;

public class CommonGoalScout implements Scout<MockCommonGoal> {
    @Override
    public void update(MockCommonGoal objects) throws RemoteException {
        view.updateCommonGoal(objects);
    }
}
