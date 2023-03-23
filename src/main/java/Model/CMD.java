package Model;
import java.time.LocalDateTime;
import java.util.*;
import java.lang.*;
public interface CMD {
    List<Tile> selectedTiles(List<Coordinates> coordinates);
    void insertTiles(String player, List<Integer> sort, List<Tile> tiles, int column);
    void writeChat(String message);
}
