package Client.View.Gui;

import Client.View.View;
import Utils.ChatMessage;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;
import Utils.Tile;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class Gui extends View {

    @Override
    public void updateBoard(MockBoard mockBoard) throws RemoteException {

    }

    @Override
    public void updateCommonGoal(List<MockCommonGoal> mockCommonGoals) throws RemoteException {

    }

    @Override
    public void updatePlayer(MockPlayer mockPlayer) throws RemoteException {

    }

    @Override
    public void updateChat(Stack<ChatMessage> chatFlow) throws RemoteException {

    }

    @Override
    public void updateLobby(String playerLogged) throws RemoteException {

    }

    @Override
    public void newTurn(String playerID) throws RemoteException {

    }

    @Override
    public void askLobbySize() throws RemoteException {

    }

    @Override
    public void outcomeSelectTiles(List<Tile> tiles) throws RemoteException {

    }

    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeWriteChat(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeException(Exception e) throws RemoteException {

    }

    @Override
    public void outcomeLogin(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeLogout(boolean success) throws RemoteException {

    }

    @Override
    public void askPlayerInfo(List<Collection<String>> lobbyInfo) throws RemoteException {

    }
}


