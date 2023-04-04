package Server.Controller;

import Server.Exception.*;
import Server.Exception.Player.NotYourTurnException;
import Server.Model.*;
import Enumeration.*;

import java.io.*;
import java.util.*;

public class GameController{
    private UUID uuid = UUID.randomUUID();
    private GameModel game;
    private PlayerAction playerAction;

    public GameController(List<String> IDs) throws FileNotFoundException {
        this.game = new GameModel(this.uuid, IDs.size(), IDs);
        this.playerAction = new PlayerAction(this.game);
    }

    public PlayerAction ableTo(OpType code, String playerID) throws PlayerException {
        if (code .equals(OpType.MESSAGES))
            return this.playerAction;
        if(playerID.equals(this.game.getCurrPlayer().getID()))
            return this.playerAction;
        else throw new NotYourTurnException(playerID);
    }

    public GamePhase getGamePhase(){
        return game.getPhase();
    }

    public void endTurn() throws EndingPhase {
        game.endTurn();
    }

    public List<Rank> endGame(){
        return game.finalRank();
    }


    public UUID getUuid() {
        return uuid;
    }

    public GameModel getGame() {
        return game;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }
}