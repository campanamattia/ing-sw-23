package Server.Controller;

import Enumeration.GamePhase;
import Enumeration.MessageType;
import Enumeration.TurnPhase;
import Exception.BoardException;
import Exception.ChatException;
import Exception.Player.NonConformingInputParametersException;
import Exception.Player.NotYourTurnException;
import Exception.PlayerException;
import Interface.GameCommand;
import Interface.View;
import Messages.Client.*;
import Messages.Client.SelectedTilesMessage;
import Messages.Server.*;
import Messages.ServerMessage;
import Server.Model.GameModel;
import Server.Model.Player;
import Server.Network.Client.SocketHandler;
import Server.Network.Lobby;
import Utils.Coordinates;
import Utils.Tile;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

public class PlayerAction extends UnicastRemoteObject implements GameCommand {
    private GameModel gameModel;
    private TurnPhase state;
    private CurrentPlayer currentPlayer;


    public PlayerAction(GameModel gameModel) throws RemoteException {
        super();
        this.gameModel  = gameModel;
        this.state= TurnPhase.PICKING;
        this.currentPlayer = new CurrentPlayer(this.gameModel.getCurrentPlayer());
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer.reset(player);
    }

    @Override
    public List<Tile> selectedTiles(String playerID, List<Coordinates> coordinates) throws RemoteException {

        return null;
    }

    @Override
    public boolean insertTiles(String playerID, List<Integer> sort, int column) throws RemoteException {

        return false;
    }

    @Override
    public boolean writeChat(String playerID, String message) throws RemoteException {

        return false;
    }


    @Override
    public void liveStatus() throws RemoteException {

    }

    private TurnPhase ableTo(String playerID) throws NotYourTurnException {
        if(!playerID.equals(this.gameModel.getCurrentPlayer().getID()))
            throw new NotYourTurnException(this.gameModel.getCurrentPlayer().getID());
        else return this.state;
    }
}
