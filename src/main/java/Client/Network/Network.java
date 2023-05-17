package Client.Network;

import Interface.Client.RemoteClient;
import Interface.Scout.BoardScout;
import Client.View.View;
import Interface.Scout.ChatScout;
import Interface.Scout.CommonGoalScout;
import Interface.Scout.PlayerScout;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;


import java.io.IOException;


public abstract class Network implements GameCommand, LobbyInterface, RemoteClient, PlayerScout, BoardScout, CommonGoalScout, ChatScout{
    protected String ipAddress;
    protected int port;
    protected View view;

    public abstract void init (String ipAddress, int ip) throws IOException;
}