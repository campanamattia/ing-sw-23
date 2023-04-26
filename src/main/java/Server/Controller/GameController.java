package Server.Controller;


import Enumeration.GamePhase;
import Exception.GamePhase.EndGameException;
import Exception.GamePhaseException;
import Exception.Player.NonConformingInputParametersException;
import Exception.PlayerNotFoundException;
import Interface.ManageConnection;
import Messages.Client.WriteChatMessage;
import Messages.Client.InsertTilesMessage;
import Messages.Client.PingMessage;
import Messages.Client.SelectedTilesMessage;
import Messages.ClientMessage;
import Messages.Server.ErrorMessage;
import Messages.ServerMessage;
import Server.Controller.Phase.EndedMatch;
import Server.Controller.Phase.LastRoundState;
import Server.Controller.Phase.NormalState;
import Server.Model.*;
import Server.Network.Client.ClientHandlerSocket;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;

public class GameController implements ManageConnection {
    private UUID uuid;
    private GameModel gameModel;
    private PhaseController phaseController;
    private static final PlayerAction playerAction;

    public GameController(PlayerAction playerAction){
        this.uuid = null;
        this.gameModel = null;
        this.phaseController = null;
        this.playerAction = playerAction;
    }

    /**
     Constructs a new GameController with a randomly generated UUID, a new GameModel with the given player IDs, and initializes its fields.
     @param IDs the list of player IDs to create a new GameModel with
     @throws IOException if there is an error creating the GameModel
     */
    public void init(List<String> IDs) throws IOException {
        this.uuid = UUID.randomUUID();
        this.gameModel = new GameModel(this.uuid, IDs);
        this.playerAction.init(gameModel);
        this.phaseController = new NormalState(this.gameModel.getCurrentPlayer(), this.gameModel.getPlayers());
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
        this.gameModel = new Gson().fromJson(reader, GameModel.class);
        this.uuid = this.gameModel.getUuid();
        GameController.playerAction = new PlayerAction();
        GameController.playerAction.init(this.gameModel);
    }


    public static ServerMessage doAction(ClientHandlerSocket client, ClientMessage message){
        return switch (message) {
            case WriteChatMessage writeChatMessage -> playerAction.writeChat(writeChatMessage);
            case SelectedTilesMessage selectedTilesMessage -> playerAction.selectedTiles(selectedTilesMessage);
            case InsertTilesMessage insertTilesMessage -> playerAction.insertTiles(insertTilesMessage);
            case PingMessage pingMessage -> playerAction.ping(pingMessage);
            default -> new ErrorMessage(new NonConformingInputParametersException().toString());
        };
    }
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
    }

    /**
     Sets the status of the player with the given ID to the given status.
     @param id the ID of the player whose status is being set
     @param status the status to set for the player (true if they are ready, false if they are not)
     @throws PlayerNotFoundException if the player with the given ID is not found in the gameModel
     */
    @Override
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