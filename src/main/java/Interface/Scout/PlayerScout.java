package Interface.Scout;

import Utils.MockObjects.MockPlayer;

import java.rmi.RemoteException;

public interface PlayerScout extends Scout {
    void update(MockPlayer mockPlayer) throws RemoteException;
}
