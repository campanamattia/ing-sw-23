package Client.Network;

import Interface.Client.RemoteClient;
import Interface.Server.GameCommand;
import Interface.Server.LobbyInterface;
import Client.View.View;
import Server.Model.Talent.*;
import Utils.Scouts.ChatScout;
import Utils.Scouts.CommonGoalScout;
import Utils.Scouts.PlayerScout;
import Utils.Scouts.BoardScout;
import Interface.Scout;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public abstract class Network implements GameCommand, LobbyInterface, RemoteClient, Scout{
    protected View view;

    protected String ipAddress;
    protected int port;

    protected Map<Class<? extends Talent>, Scout> scouts;

    public Network(View view) {
        this.view = view;
        this.scouts = new HashMap<>();
        this.scouts.put(PlayerTalent.class, new PlayerScout(this.view));
        this.scouts.put(BoardTalent.class, new BoardScout(this.view));
        this.scouts.put(CommonGoalTalent.class, new CommonGoalScout(this.view));
        this.scouts.put(ChatTalent.class, new ChatScout(this.view));
    }

    public abstract void init (String ipAddress, int ip) throws IOException;
}