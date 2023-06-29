package Exception.Player;

import Exception.PlayerException;

/**
 The {@code NotYourTurnException} class is an exception that is thrown when a player tries to perform an action
 when it is not their turn.
 It extends the {@link PlayerException} class and provides the player's ID as part of the error message.
 */
public class NotYourTurnException extends PlayerException {

    /**
     Constructs a new {@code NotYourTurnException} with the specified player ID.
     @param playerID the ID of the player whose turn it currently is
     */
    public NotYourTurnException(String playerID) {
        super("You have to wait, now it's "+playerID+"'s turn");
    }
}
