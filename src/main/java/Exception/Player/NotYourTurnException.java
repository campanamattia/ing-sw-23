package Exception.Player;

import Exception.PlayerException;

public class NotYourTurnException extends PlayerException {
    public NotYourTurnException(String playerID) {
        super("You have to wait, now it's "+playerID+"'s turn");
    }
}
