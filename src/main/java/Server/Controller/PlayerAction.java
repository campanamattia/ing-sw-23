package Server.Controller;

import Enumeration.GamePhase;
import Exception.BoardException;
import Exception.ChatException;
import Exception.Player.NonConformingInputParametersException;
import Exception.Player.NotYourTurnException;
import Exception.PlayerException;
import Interface.GameCommand;
import Messages.Client.InsertTiles;
import Server.Model.Coordinates;
import Server.Model.GameModel;
import Server.Model.Tile;

import java.util.List;

public class PlayerAction implements GameCommand {
    private final GameModel game;
    private GamePhase state = GamePhase.PICKING;

    public PlayerAction(GameModel game) {
        this.game = game;
    }

    
    @Override
    public List<Tile> selectedTiles(String playerID, List<Coordinates> coordinates) throws BoardException, PlayerException {
        try {
            if (GamePhase.PICKING == ableTo(playerID)){
                List<Tile> tiles = game.selectedTiles(coordinates);
                this.state = GamePhase.INSERTING;
                return tiles;
            }
            else throw new NonConformingInputParametersException();
        }catch (BoardException | PlayerException e){
            System.out.println(e.toString());
            throw e;
        }
    }


    @Override
    public void insertTiles(InsertTiles message) throws PlayerException {
        try{
            if(GamePhase.INSERTING==ableTo(message.getPlayerID())) {
                game.insertTiles(message.getSorted(), currentPlayer.getListTiles(), message.getColumn());
                this.state = GamePhase.PICKING;
            }
            else throw new NonConformingInputParametersException();
        }catch (PlayerException e){
            System.out.println(e.toString());
            throw e;
        }
    }


    @Override
    public void writeChat(String message) throws ChatException {
        try{
            game.writeChat(message);
        }catch(ChatException e){
            System.out.println(e.toString());
            throw e;
        }
    }


    private GamePhase ableTo(String playerID) throws PlayerException {
       if(!playerID.equals(this.game.getCurrentPlayer().getID()))
           throw new NotYourTurnException(this.game.getCurrentPlayer().getID());
       else return this.state;
    }
}
