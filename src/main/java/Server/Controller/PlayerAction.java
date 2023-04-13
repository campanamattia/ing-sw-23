package Server.Controller;

import Exception.BoardException;
import Exception.ChatException;
import Exception.Player.ColumnNotValidException;
import Exception.PlayerException;
import Interface.CMD;
import Server.Model.Coordinates;
import Server.Model.GameModel;
import Server.Model.Tile;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerAction implements CMD {
    private final GameModel game;

    public PlayerAction(GameModel game) {
        this.game = game;
    }

    @Override
    public List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException {
        try{
            return game.selectedTiles(coordinates);
        }catch (BoardException e) {
            System.out.println(e.toString());
            throw e;
        }
    }

    @Override
    public void insertTiles(List<Integer> sort, List<Tile> tiles, int column) throws PlayerException {
        try{
            game.insertTiles(sort, tiles, column);
        }catch (ColumnNotValidException e){
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
}
