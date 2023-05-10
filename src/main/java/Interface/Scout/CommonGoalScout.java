package Interface.Scout;

import Utils.MockObjects.MockCommonGoal;

import java.rmi.RemoteException;

public interface CommonGoalScout extends Scout {
    void update(MockCommonGoal mockCommonGoal) throws RemoteException;
}
