package Interface.Scout;

import Utils.MockObjects.MockBoard;

import java.rmi.RemoteException;

public interface BoardScout extends Scout<MockBoard> {
    @Override
    void update(MockBoard objects) throws RemoteException;
}
