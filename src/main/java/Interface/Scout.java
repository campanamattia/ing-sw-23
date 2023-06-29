package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Scout interface represents a remote interface for updating objects.
 * It is used to notify remote observers about changes in objects.
 *
 * @param <O> the type of the objects to be updated.
 */
public interface Scout<O> extends Remote {

    /**
     * Updates the remote observers with the specified objects.
     *
     * @param objects the objects to be updated.
     * @throws RemoteException if a remote communication error occurs.
     */
    void update(O objects) throws RemoteException;
}
