package Exception.Player;

import Exception.PlayerException;

public class PlayerNotFoundException extends PlayerException {
    public PlayerNotFoundException(String id) {
        super(id+" doesn't exist!");
    }
}
