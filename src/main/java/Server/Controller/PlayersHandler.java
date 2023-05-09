package Server.Controller;

import Enumeration.TurnPhase;
import Exception.Player.NotYourTurnException;
import Interface.GameCommand;
import Server.Model.GameModel;
import Server.Model.Player;
import Server.Network.ClientHandler;
import Server.ServerApp;
import Utils.Coordinates;
import Utils.Tile;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
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
    public void writeChat(String playerID, String message) throws RemoteException {


    }

    @Override
    public void liveStatus(String playerID) throws RemoteException {


    }

    @Override
    public void logOut(String playerID) throws RemoteException {


    }

    private TurnPhase ableTo(String playerID) throws NotYourTurnException {
        if(!playerID.equals(this.gameModel.getCurrentPlayer().getID()))
            throw new NotYourTurnException(this.gameModel.getCurrentPlayer().getID());
        else return this.state;
    }


}
