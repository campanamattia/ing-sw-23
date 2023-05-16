package Client.Network;

import Interface.Scout.BoardScout;
import Interface.Scout.CommonGoalScout;
import Interface.Scout.PlayerScout;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Messages.ClientMessage;
import Messages.ServerMessage;
import Utils.Coordinates;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.List;

public abstract class Network {
    protected String ipAddress;
    protected int port;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public abstract void writeChat(String playerID, String text) throws RemoteException;

    public abstract void init (int port, String ipAddress) throws IOException;

    public abstract void selectTiles(String playerID, List<Coordinates> coordinates) throws RemoteException;

    public abstract void insertTiles(String playerID, List<Integer> sorted, int column) throws RemoteException;

    public abstract void sendMessage(ClientMessage clientMessage) throws IOException;




}