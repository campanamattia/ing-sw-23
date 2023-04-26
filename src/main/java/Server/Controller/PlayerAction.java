package Server.Controller;

import Enumeration.GamePhase;
import Enumeration.MessageType;
import Exception.BoardException;
import Exception.ChatException;
import Exception.Player.NonConformingInputParametersException;
import Exception.Player.NotYourTurnException;
import Exception.PlayerException;
import Interface.GameCommand;
import Messages.Client.*;
import Messages.Client.SelectedTilesMessage;
import Messages.Server.*;
import Messages.ServerMessage;
import Server.Model.GameModel;
import Server.Model.Player;
import Server.Network.Lobby;
import Utils.Tile;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class PlayerAction extends UnicastRemoteObject implements GameCommand {
    private GameModel gameModel;
    private GamePhase state;
    private CurrentPlayer currentPlayer;

    public PlayerAction() throws RemoteException {
        super();
        this.gameModel = null;
        this.state = null;
        this.currentPlayer = null;
    }

    public void init(GameModel gameModel){
        this.gameModel  = gameModel;
        this.currentPlayer = new CurrentPlayer(this.gameModel.getCurrentPlayer());
        this.state= GamePhase.PICKING;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer.reset(player);
    }

    @Override
    public ServerMessage selectedTiles(SelectedTilesMessage message){
        try {
            if (GamePhase.PICKING == ableTo(message.getPlayerID())){
                this.currentPlayer.setTiles(gameModel.selectedTiles(message.getCoordinates()));
                this.state = GamePhase.INSERTING;
                return new Messages.Server.SelectedTilesMessage(this.currentPlayer.getTiles());
            }
            else {
                return new ErrorMessage(new NonConformingInputParametersException("Not the right moment").toString());
            }
        }catch (BoardException | PlayerException e){
            System.out.println(e.toString());
            return new ErrorMessage(e.toString());
        }
    }

    @Override
    public ServerMessage insertTiles(InsertTilesMessage message){
        try{
            if(GamePhase.INSERTING==ableTo(message.getPlayerID())) {
                gameModel.insertTiles(message.getSorted(), currentPlayer.getTiles(), message.getColumn());
                this.state = GamePhase.PICKING;
                return new InsertedTilesMessage();
            }
            else
                return new ErrorMessage(new NonConformingInputParametersException("Not the right moment").toString());
        }catch (PlayerException e){
            System.out.println(e.toString());
            return new ErrorMessage(e.toString());
        }
    }

    @Override
    public ServerMessage writeChat(WriteChatMessage message){
        try{
            gameModel.writeChat(message.getPlayerID(), message.getText());
            return new ChatRoomMessage(this.gameModel.getChatRoom().getFlow());
        }catch(ChatException e){
            System.out.println(e.toString());
            return new ErrorMessage(e.toString());
        }
    }

    @Override
    public ServerMessage LogIn(AddPlayerMessage message) {
        if(Lobby.canLog(message.getPlayerID()))
            return new AddedPlayerMessage();
        return new ErrorMessage("Player ID already token, choose another one");
    }

    @Override
    public StatusMessage doScreenShot() {
        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setMessageType(MessageType.SCREENSHOT);
        statusMessage.setBoard(this.gameModel.getBoard().getBoard());
        HashMap<String, Tile[][]> shelves = null;
        for(Player player : this.gameModel.getPlayers())
            shelves.put(player.getID(), player.getMyShelf().getMyShelf());
        statusMessage.setShelves(shelves);
        statusMessage.setCurrentPlayer(this.currentPlayer.getCurrentPlayer().getPlayerID());
        return statusMessage;
    }

    @Override
    public ServerMessage ping(PingMessage message) {
        return GameCommand.super.ping(message);
    }

    private GamePhase ableTo(String playerID) throws PlayerException {
       if(!playerID.equals(this.gameModel.getCurrentPlayer().getID()))
           throw new NotYourTurnException(this.gameModel.getCurrentPlayer().getID());
       else return this.state;
    }

}
