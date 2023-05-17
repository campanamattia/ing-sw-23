package Messages;

import Client.View.View;

import java.io.Serializable;

public abstract class ServerMessage implements Serializable {

    public abstract void execute(View view);
}