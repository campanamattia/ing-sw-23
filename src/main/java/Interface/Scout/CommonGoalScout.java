package Interface.Scout;

import Utils.MockObjects.MockCommonGoal;

import java.rmi.RemoteException;

public interface CommonGoalScout extends Scout<MockCommonGoal> {
    @Override
    void update(MockCommonGoal objects) throws RemoteException;
}
