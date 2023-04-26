package Server.Network;

import Server.Controller.GameController;
import Server.Controller.PlayerAction;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class Lobby implements Runnable {
    private static List<String> playersID;
    private static int lobbySize;
    private final PlayerAction playerAction;
    private final GameController gameController;

    public Lobby() throws RemoteException {
        playersID = null;
        this.lobbySize = 5;
        this.playerAction = new PlayerAction();
        this.gameController = new GameController(playerAction);
    }

    @Override
    public void run() {
        while(true)
            if(lobbySize == playersID.size())
                break;
        initGame();
    }

    private void initGame() {
        try {
            gameController.init(playersID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean canLog(String playerID) {
        if (!playersID.contains(playerID)) {
            playersID.add(playerID);
            return true;
        } else return false;
    }

    public PlayerAction getPlayerAction() {
        return this.playerAction;
    }

    public static void setLobbySize(int lobbySize){
        Lobby.lobbySize = lobbySize;
    }
}
