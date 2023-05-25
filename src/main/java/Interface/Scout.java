package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Scout<O> extends Remote {
    void update(O objects) throws RemoteException;
}
