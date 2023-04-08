package Interface;

import java.rmi.Remote;
import java.util.*;
import java.lang.*;
import Exception.*;
import Server.Model.Coordinates;
import Server.Model.Tile;

public interface CMD extends Remote {
    List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException;

    void insertTiles(List<Integer> sort, List<Tile> tiles, int column) throws PlayerException;

    void writeChat(String message) throws ChatException;
}