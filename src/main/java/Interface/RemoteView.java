package Interface;

import Server.Model.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;
import Utils.Tile;

import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

public interface RemoteView extends Remote {

    void updateBoard(MockBoard mockBoard) throws RemoteException;

    void updateCommonGoal(List<MockCommonGoal> mockCommonGoals) throws RemoteException;

    void updatePlayer(MockPlayer mockPlayer) throws RemoteException;

    void updateChat(Stack<ChatMessage> chatFlow) throws RemoteException;

    void updateLobby(String playerLogged) throws RemoteException;

    void newTurn(String playerID) throws RemoteException;

    void askLobbySize() throws RemoteException;

    void outcomeSelectTiles(List<Tile> tiles) throws RemoteException;

    void outcomeInsertTiles(boolean success) throws RemoteException;

    void outcomeWriteChat(boolean success) throws RemoteException;

    void outcomeException(Exception e) throws RemoteException;

    void outcomeLogin(boolean success) throws RemoteException;

    void outcomeLogout(boolean success) throws RemoteException;

    void askPlayerID() throws RemoteException;
}
