package Utils.Scouts;

import Client.View.View;
import Interface.Scout;
import Utils.MockObjects.MockCommonGoal;

import java.rmi.RemoteException;

public class CommonGoalScout implements Scout<MockCommonGoal> {
    View view;

    public CommonGoalScout(View view) {
        this.view = view;
    }

    @Override
    public void update(MockCommonGoal objects) throws RemoteException {
        this.view.updateCommonGoal(objects);
    }
}
