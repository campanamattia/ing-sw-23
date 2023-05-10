package Interface.Scout;

import Utils.MockObjects.MockBoard;

import java.rmi.RemoteException;

public interface BoardScout extends Scout {
        void update(MockBoard mockBoard) throws RemoteException;
}
