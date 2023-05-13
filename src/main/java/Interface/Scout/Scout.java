package Interface.Scout;

import java.rmi.Remote;

public interface Scout extends Remote {
    void update(Object object);
}
