package Server.Controller;


import Exception.GamePhase.EndGameException;
import Exception.GamePhaseException;
import Exception.PlayerException;
import Exception.PlayerNotFoundException;
import Exception.Player.NotYourTurnException;
import Interface.ManageConnection;
import Server.Controller.Phase.EndedMatch;
import Server.Controller.Phase.LastRoundState;
import Server.Controller.Phase.NormalState;
import Server.Model.*;
import Enumeration.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class GameController implements ManageConnection {
    private final UUID uuid;
    private final GameModel game;
    private PhaseController phaseController;
    private final PlayerAction playerAction;

    public GameController(List<String> IDs) throws FileNotFoundException {
        this.uuid = UUID.randomUUID();
        this.game = new GameModel(this.uuid, IDs);
        this.playerAction = new PlayerAction(this.game);
        this.phaseController = new NormalState(this.game.getCurrPlayer(), this.game.getPlayers());
    }

    public GameController(String filepath) throws FileNotFoundException {
        try{
            JsonReader reader = new JsonReader(new FileReader(filepath));
            this.game = new Gson().fromJson(reader, GameModel.class);
            this.uuid = this.game.getUuid();
            this.playerAction = new PlayerAction(this.game);
            this.phaseController = null;
        } catch(FileNotFoundException e){
            System.out.println("The path isn't valid");
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

    public void endShift(){
        phaseController.endTurn(this.game.getCommonGoals());
        do{
            try{
                phaseController.nextPlayer();
                break;
            }catch (GamePhaseException e){ //must implement new equals in gamephaseexception
                if (e.equals(new EndGameException())) {
                    this.game.setRanking(new EndedMatch().doRank(this.phaseController.getPlayers()));

                }
                else
                    this.phaseController = new LastRoundState(this.phaseController.getCurrentPlayer(), this.phaseController.getPlayers());
            }
        }while(true);
    }

    @Override
    public void setPlayerStatus(String id, Boolean status) throws PlayerNotFoundException {
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