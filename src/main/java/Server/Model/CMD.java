package Server.Model;

import java.util.*;
import java.lang.*;
import Server.Exception.*;
import Server.Exception.BoardException;
import Server.Exception.PlayerException;

public interface CMD {
    List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException;
    void insertTiles(String player, List<Integer> sort, List<Tile> tiles, int column) throws PlayerException;
    void writeChat(String message);
}
