package Utils.Scouts;

import Client.View.View;
import Interface.Scout;
import Utils.MockObjects.MockBoard;

import java.rmi.RemoteException;

public class BoardScout implements Scout<MockBoard> {
    View view;

    public BoardScout(View view) {
        this.view = view;
    }

    @Override
    public void update(MockBoard objects) throws RemoteException {
        this.view.updateBoard(objects);
    }
}
