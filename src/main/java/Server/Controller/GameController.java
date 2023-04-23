package Server.Controller;


import Enumeration.GamePhase;
import Exception.GamePhase.EndGameException;
import Exception.GamePhaseException;
import Exception.PlayerNotFoundException;
import Interface.ManageConnection;
import Messages.ClientMessage;
import Server.Controller.Phase.EndedMatch;
import Server.Controller.Phase.LastRoundState;
import Server.Controller.Phase.NormalState;
import Server.Model.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class GameController implements ManageConnection {
    private final UUID uuid;
    private final GameModel game;
    private PhaseController phaseController;
    private final PlayerAction playerAction;

    /**
     Constructs a new GameController with a randomly generated UUID, a new GameModel with the given player IDs, and initializes its fields.
     @param IDs the list of player IDs to create a new GameModel with
     @throws IOException if there is an error creating the GameModel
     */
    public GameController(List<String> IDs) throws IOException {
        this.uuid = UUID.randomUUID();
        try{
            this.game = new GameModel(this.uuid, IDs);
        }catch(IOException e){
            System.out.println(e.toString());
            throw e;
        }
        this.playerAction = new PlayerAction(game);
        this.phaseController = new NormalState(this.game.getCurrentPlayer(), this.game.getPlayers());
    }

    /**
     Constructs a new GameController by reading in a JSON file from the given file path and initializing its fields.
     @param filepath the file path of the JSON file to read in
     @throws FileNotFoundException if the given file path is not valid
     */
    public GameController(String filepath) throws FileNotFoundException {
        JsonReader reader;
        try{
            reader = new JsonReader(new FileReader(filepath));
        } catch(FileNotFoundException e){
            System.out.println("The path isn't valid");
            throw e;
        }
        this.game = new Gson().fromJson(reader, GameModel.class);
        this.uuid = this.game.getUuid();
        this.playerAction = new PlayerAction(game);
    }

    public void doAction(ClientMessage message){
        switch(message.getOperationType()){
            case MESSAGES -> playerAction.writeChat(ClientMessage message);
            case INSERTTILES -> playerAction.insertTiles(ClientMessage message);
            case SELECTEDTILES -> playerAction.selectedTiles(ClientMessage message);
        }
    }
    /**
     This method ends the current turn, checks for common goals, advances to the next player, and updates the game status.
     If the game has entered its last round, it changes the game phase accordingly.
     If the game has ended, it sets the leaderboard and game phase to ended.
     @throws GamePhaseException if there is an error transitioning between game phases
     @throws IOException if there is an error updating the JSON file
     */
    public void endTurn(){
        phaseController.checkCommonGoals(this.game.getCommonGoals());
        do{
            try{
                phaseController.nextPlayer();
                this.game.setCurrentPlayer(this.phaseController.getCurrentPlayer());
                break;
            }catch (GamePhaseException e){
                if (e instanceof EndGameException) {
                    this.game.setLeaderboard(new EndedMatch().doRank(this.phaseController.getPlayers()));
                    this.game.setPhase(GamePhase.ENDED);
                }
                else {
                    this.phaseController = new LastRoundState(this.phaseController.getCurrentPlayer(), this.phaseController.getPlayers());
                    this.game.setPhase(phaseController.getPhase());
                }
            }finally {
                try{
                    this.game.updateStatus();
                } catch (IOException e){
                    System.out.println("An error occurred updating the json file.");

                }
            }
        }while(true);
    }

    /**
     Sets the status of the player with the given ID to the given status.
     @param id the ID of the player whose status is being set
     @param status the status to set for the player (true if they are ready, false if they are not)
     @throws PlayerNotFoundException if the player with the given ID is not found in the game
     */
    @Override
    public void setPlayerStatus(String id, Boolean status) throws PlayerNotFoundException {
        try{
            this.game.getPlayer(id).setStatus(status);
        }catch (PlayerNotFoundException e){
            System.out.println(e.toString());
        }
    }

    /**
     Returns the UUID associated with this GameController.
     @return the UUID associated with this GameController
     */
    public UUID getUuid() {
        return uuid;
    }
    /**
     Returns the GameModel associated with this GameController.
     @return the GameModel associated with this GameController
     */
    public GameModel getGame() {
        return game;
    }
    /**
     Returns the PlayerAction associated with this GameController.
     @return the PlayerAction associated with this GameController
     */
    public PlayerAction getPlayerAction() {
        return playerAction;
    }
}