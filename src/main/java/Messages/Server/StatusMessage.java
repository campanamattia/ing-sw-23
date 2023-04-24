package Messages.Server;

import Enumeration.MessageType;
import Messages.ServerMessage;
import Utils.Cell;
import Utils.Tile;

import java.util.HashMap;

public class StatusMessage extends ServerMessage {
    private Cell[][] board;
    private HashMap<String, Tile[][]> shelves;
    private String currentPlayer;

    public StatusMessage(){
        super();
        this.board = null;
        this.shelves = null;
        this.currentPlayer = null;
    }

    public StatusMessage(MessageType messageType, Cell[][] board, HashMap<String, Tile[][]> shelves, String currentPlayer){
        this.messageType = messageType;
        this.board = board;
        this.shelves = shelves;
        this.currentPlayer = currentPlayer;
    }

    public StatusMessage(Cell[][] board, HashMap<String, Tile[][]> shelves, String currentPlayer) {
        this.messageType = MessageType.NEWTURN;
        this.board = board;
        this.shelves = shelves;
        this.currentPlayer = currentPlayer;
    }

    public Cell[][] getBoard() {
        return this.board;
    }
    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public HashMap<String, Tile[][]> getShelves() {
        return this.shelves;
    }
    public void setShelves(HashMap<String, Tile[][]> shelves) {
        this.shelves = shelves;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
