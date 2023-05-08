package Server.Controller;


import Enumeration.GamePhase;
import Exception.GamePhase.EndGameException;
import Exception.GamePhaseException;
import Exception.PlayerNotFoundException;
import Messages.ClientMessage;
import Messages.ServerMessage;
import Server.Controller.Phase.EndedMatch;
import Server.Controller.Phase.LastRoundState;
import Server.Controller.Phase.NormalState;
import Server.Model.*;
import Server.Network.ClientHandler;
import Server.ServerApp;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class GameController {
    private UUID uuid;
    private GameModel gameModel;
    private HashMap<String, ClientHandler> players;
    private PhaseController phaseController;
    private final PlayerAction playerAction;

    public GameController(List<String> playersID){
        this.uuid = UUID.randomUUID();
        try {
            this.gameModel = new GameModel(this.uuid, playersID);
            this.playerAction = new PlayerAction(this.gameModel);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.toString());
            throw new RuntimeException(e);
        }
        this.phaseController = new NormalState(this.gameModel.getCurrentPlayer(), this.gameModel.getPlayers());
    }

    /**
     Constructs a new GameController by reading in a JSON file from the given file path and initializing its fields.
     @param filepath the file path of the JSON file to read in
     @throws FileNotFoundException if the given file path is not valid
     */

    /*public GameController(String filepath) throws FileNotFoundException {
        JsonReader reader;
        try{
-            reader = new JsonReader(new FileReader(filepath));
        } catch(FileNotFoundException e){
            System.out.println("The path isn't valid");
            throw e;
        }
        this.gameModel = new Gson().fromJson(reader, GameModel.class);
        this.uuid = this.gameModel.getUuid();
        GameController.playerAction = new PlayerAction();
        GameController.playerAction.init(this.gameModel);
    }
    */
    /**
     This method ends the current turn, checks for common goals, advances to the next player, and updates the gameModel status.
     If the gameModel has entered its last round, it changes the gameModel phase accordingly.
     If the gameModel has ended, it sets the leaderboard and gameModel phase to ended.
     */
    public void endTurn() throws IOException {
        phaseController.checkCommonGoals(this.gameModel.getCommonGoals());
        do {
            try {
                phaseController.nextPlayer();
                this.gameModel.setCurrentPlayer(this.phaseController.getCurrentPlayer());
                GameController.playerAction.setCurrentPlayer(this.phaseController.getCurrentPlayer());
                break;
            } catch (GamePhaseException e) {
                if (e instanceof EndGameException) {
                    this.gameModel.setLeaderboard(new EndedMatch().doRank(this.phaseController.getPlayers()));
                    this.gameModel.setPhase(GamePhase.ENDED);
                    break;
                } else {
                    this.phaseController = new LastRoundState(this.phaseController.getCurrentPlayer(), this.phaseController.getPlayers());
                    this.gameModel.setPhase(phaseController.getPhase());
                }
                continue;
            } finally {
                this.gameModel.updateStatus();
            }
        }while(true);
        // TODO: 08/05/2023 work on how send a message to all players
    }

    /**
     Sets the status of the player with the given ID to the given status.
     @param id the ID of the player whose status is being set
     @param status the status to set for the player (true if they are ready, false if they are not)
     @throws PlayerNotFoundException if the player with the given ID is not found in the gameModel
     */
    public void setPlayerStatus(String id, Boolean status) throws PlayerNotFoundException {
        try{
            this.gameModel.getPlayer(id).setStatus(status);
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
    public GameModel getGameModel() {
        return gameModel;
    }

}