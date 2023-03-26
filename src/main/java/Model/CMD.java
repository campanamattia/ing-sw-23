package Model;

import java.util.*;
import java.lang.*;
import Exception.*;
public interface CMD {
    List<Tile> selectedTiles(List<Coordinates> coordinates) throws BoardException;
    void insertTiles(String player, List<Integer> sort, List<Tile> tiles, int column);
    void writeChat(String message);
}
