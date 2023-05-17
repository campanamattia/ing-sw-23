package Interface.Scout;

import Utils.MockObjects.MockPlayer;

import java.rmi.RemoteException;


public interface PlayerScout extends Scout<MockPlayer> {
    @Override
    void update(MockPlayer objects) throws RemoteException;
}

