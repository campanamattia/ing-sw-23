package Interface;

import java.rmi.Remote;
import java.util.*;
import java.lang.*;
import Exception.*;
import Messages.Client.InsertTiles;
import Messages.ClientMessage;
import Server.Model.Coordinates;
import Server.Model.Tile;

public interface GameCommand extends Remote {

    List<Tile> selectedTiles(String playerID, List<Coordinates> coordinates) throws BoardException, PlayerException;

    void insertTiles(String playerID, List<Integer> sort, List<Tile> tiles, int column) throws PlayerException;

    void insertTiles(ClientMessage message) throws PlayerException;

    void insertTiles(InsertTiles message) throws PlayerException;

    void writeChat(String message) throws ChatException;
}