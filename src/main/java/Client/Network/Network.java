package Client.Network;

import Messages.ClientMessage;
import Messages.ServerMessage;

import java.io.IOException;
import java.security.SecureRandom;

public abstract class Network implements {
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

    public abstract ServerMessage sendMessage(ClientMessage clientMessage) throws IOException;
}