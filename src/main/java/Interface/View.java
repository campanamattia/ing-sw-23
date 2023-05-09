package Interface;

import java.rmi.Remote;

import Messages.ServerMessage;
import Server.Controller.PlayersHandler;

public interface View extends Remote {

    void askServerInfo();

    void askNickname();

    void askNumberPlayer();

    void askAction(PlayersHandler playerActions);

    void printCommonGoal();

    void printBoard();

    void printShelf();

    void displayEndgameResult(String winner, String condition);

    void displayDisconnectionMessage(String disconnectedNickname, String message);

    void displayErrorAndExit(String message);

    void displayMessage(String message);

    void newTurn(ServerMessage message);

    void endTurn(ServerMessage message);

}
