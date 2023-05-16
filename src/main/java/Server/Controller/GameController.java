package Server.Controller;


import Enumeration.GamePhase;
import Exception.GamePhase.EndGameException;
import Exception.GamePhaseException;
import Server.Controller.Phase.EndedMatch;
import Server.Controller.Phase.LastRoundState;
import Server.Controller.Phase.NormalState;
import Server.Controller.Phase.PhaseController;
import Server.Controller.Players.PlayersHandler;
import Server.Model.*;
import Server.Network.Client.ClientHandler;
import Server.ServerApp;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class GameController {
    private UUID uuid;
    private GameModel gameModel;
    private HashMap<String, ClientHandler> players;
    private PhaseController phaseController;
    private final PlayersHandler playersHandler;

    public GameController(HashMap<String, ClientHandler> players){
        this.uuid = UUID.randomUUID();
        this.players = players;
        List<String> playersID = new ArrayList<>(players.keySet());
        try {
            this.gameModel = new GameModel(this.uuid, playersID);
            this.playersHandler = new PlayersHandler(this.gameModel);
        } catch (IOException e) {
            ServerApp.logger.log(Level.SEVERE, e.toString());
            throw new RuntimeException(e);
        }
        this.phaseController = new NormalState(this.gameModel.getCurrentPlayer(), this.gameModel.getPlayers());
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
                GameController.playersHandler.setCurrentPlayer(this.phaseController.getCurrentPlayer());
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

    public boolean reloadPlayer()

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