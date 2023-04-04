package Interface;

import Exception.PlayerNotFoundException;

public interface ManageConnection {
    void setPlayerStatus(String id, Boolean status) throws PlayerNotFoundException;
}