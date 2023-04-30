package Client.Network;

import Messages.ClientMessage;

import java.io.IOException;

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

    public synchronized void sendMessage(ClientMessage clientMessage) throws IOException {}
}