package Interface;

import Exception.PlayerNotFoundException;

public interface ManageConnection {
    void setStatus(String id, Boolean status) throws PlayerNotFoundException;
}