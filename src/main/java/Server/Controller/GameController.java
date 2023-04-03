package Server.Controller;


import Exception.PlayerException;
import Exception.PlayerNotFoundException;
import Exception.EndingPhase;
import Exception.Player.NotYourTurnException;
import Interface.ManageConnection;
import Server.Model.*;
import Enumeration.*;

import java.io.*;
import java.util.*;

public class GameController implements ManageConnection {
    private UUID uuid = UUID.randomUUID();
    private GameModel game;
    private PlayerAction playerAction;

    public GameController(List<String> IDs) throws FileNotFoundException {
        this.game = new GameModel(this.uuid, IDs);
        this.playerAction = new PlayerAction(this.game);
    }

    public GameController(UUID uuid) throws FileNotFoundException {
        try{
            this.uuid = uuid;
            this.game = new GameModel(uuid);
            this.playerAction = new PlayerAction(this.game);
        } catch(FileNotFoundException e){
            System.out.println("The UUID isn't valid");
            throw e;
        }
    }

    public PlayerAction ableTo(OpType code, String playerID) throws PlayerException {
        if (code.equals(OpType.MESSAGES))
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

    @Override
    public void setStatus(String id, Boolean status) throws PlayerNotFoundException {
        try{
            this.game.getPlayer(id).setStatus(status);
        }catch (PlayerNotFoundException e){
            System.out.println(e.toString());
        }
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