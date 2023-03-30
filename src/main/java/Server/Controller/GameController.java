package Server.Controller;

import Server.Exception.Player.NotYourTurnException;
import Server.Exception.*;
import Server.Model.*;

import java.io.FileNotFoundException;
import java.util.*;

public class GameController{
    private UUID uuid = UUID.randomUUID();
    private GameModel game;
    private PlayerAction playerAction;
    public GameController(List<String> IDs) throws FileNotFoundException {
        this.game = new GameModel(this.uuid, IDs.size(), IDs);
        this.playerAction = new PlayerAction(this.game);
    }

    public PlayerAction ableTo(String playerID) throws PlayerException {
        if(playerID.equals(this.game.getCurrPlayer()))
            return this.playerAction;
        else throw new NotYourTurnException(playerID);
    }
}
