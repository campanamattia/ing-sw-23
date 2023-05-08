package Interface;

import Enumeration.MessageType;
import Server.Model.ChatMessage;
import Utils.Cell;
import Utils.Rank;
import Utils.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public interface ClientInterface {
    public void showBoard(Cell[][] board);
    public void showChat(Stack<ChatMessage> flow);
    public void showTitle();
    public void showMessage();
    public void showHelp();
    public void showTile(List<Tile> tiles);
    public void showStatus(Cell[][] board, HashMap<String, Tile[][]> shelves);
    public void showWinner(List<Rank> rank);
}
