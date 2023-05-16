package Interface.Scout;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Scout<O extends Object> extends Remote {
    void update(O objects) throws RemoteException;
}
