package Exception.Player;

import Exception.PlayerException;

/**
 The {@code PlayerNotFoundException} class is an exception that is thrown when a player with a specified ID
 cannot be found.
 It extends the {@link PlayerException} class and provides the player's ID as part of the error message.
 */
public class PlayerNotFoundException extends PlayerException {

    /**
     Constructs a new {@code PlayerNotFoundException} with the specified player ID.
     @param id the ID of the player that was not found
     */
    public PlayerNotFoundException(String id) {
        super(id+" doesn't exist!");
    }
}
