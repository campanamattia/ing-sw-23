package Client.Network.Scouts;

import Interface.Scout;
import static Client.ClientApp.view;
import Utils.MockObjects.MockBoard;

import java.rmi.RemoteException;

public class BoardScout implements Scout<MockBoard> {

    @Override
    public void update(MockBoard objects) throws RemoteException {
        view.updateBoard(objects);
    }
}
