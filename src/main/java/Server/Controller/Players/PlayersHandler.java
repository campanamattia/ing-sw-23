package Server.Controller.Players;

import Enumeration.TurnPhase;
import Exception.Player.NotYourTurnException;
import Interface.Scout.ChatScout;
import Interface.Server.GameCommand;
import Interface.Scout.BoardScout;
import Interface.Scout.CommonGoalScout;
import Interface.Scout.PlayerScout;
import Server.Model.GameModel;
import Server.Model.Player.Player;
import Server.Network.Client.ClientHandler;
import Server.ServerApp;
import Utils.Coordinates;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class PlayersHandler extends UnicastRemoteObject implements GameCommand {
    private TurnPhase state;
    private GameModel gameModel;
    private HashMap<String, ClientHandler> players;
    private CurrentPlayer currentPlayer;



    public PlayersHandler(GameModel gameModel, HashMap<String, ClientHandler> players) throws RemoteException {
        super();
        this.state= TurnPhase.PICKING;
        this.gameModel  = gameModel;
        this.players = players;
        this.currentPlayer = new CurrentPlayer(this.gameModel.getCurrentPlayer());
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer.reset(player);
    }

    @Override
    public void selectedTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {


    }

    @Override
    public void insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException {


    }

    @Override
    public  synchronized void writeChat(String playerID, String message) throws RemoteException {


    }

    @Override
    public void liveStatus(String playerID) throws RemoteException {


    }

    @Override
    public void logOut(String playerID) throws RemoteException{
        try{
            this.gameModel.getPlayer(playerID).setStatus(false);
        }catch (Exception e){
            ServerApp.logger.log(Level.SEVERE, e.getMessage(), e);
            this.players.get(playerID).outcomeException(e);
        }
    }

    @Override
    public synchronized void addSubscriber(Object object) throws RemoteException {
            this.gameModel.addBoardScout((BoardScout) object);
            this.gameModel.addChatScout((ChatScout) object);
            this.gameModel.addPlayerScout((PlayerScout) object);
            this.gameModel.addCommonGoalScout((CommonGoalScout) object);
    }

    private TurnPhase ableTo(String playerID) throws NotYourTurnException {
        if(!playerID.equals(this.currentPlayer.getCurrentPlayer().getPlayerID()))
            throw new NotYourTurnException(this.gameModel.getCurrentPlayer().getPlayerID());
        else return this.state;
    }
}
