package Server.Controller;

import Enumeration.GamePhase;
import Enumeration.MessageType;
import Exception.BoardException;
import Exception.ChatException;
import Exception.Player.NonConformingInputParametersException;
import Exception.Player.NotYourTurnException;
import Exception.PlayerException;
import Interface.GameCommand;
import Messages.Client.WriteChatMessage;
import Messages.Client.InsertTilesMessage;
import Messages.Client.SelectedTilesMessage;
import Messages.Server.ChatRoomMessage;
import Messages.Server.ErrorMessage;
import Messages.Server.InsertedTilesMessage;
import Messages.Server.StatusMessage;
import Messages.ServerMessage;
import Server.Model.GameModel;
import Server.Model.Player;
import Utils.Tile;

import java.util.HashMap;

public class PlayerAction implements GameCommand {
    private final GameModel gameModel;
    private GamePhase state = GamePhase.PICKING;
    private final CurrentPlayer currentPlayer;


    public PlayerAction(GameModel gameModel) {
        this.gameModel = gameModel;
        this.currentPlayer = new CurrentPlayer(this.gameModel.getCurrentPlayer());
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


    private GamePhase ableTo(String playerID) throws PlayerException {
       if(!playerID.equals(this.gameModel.getCurrentPlayer().getID()))
           throw new NotYourTurnException(this.gameModel.getCurrentPlayer().getID());
       else return this.state;
    }

}
