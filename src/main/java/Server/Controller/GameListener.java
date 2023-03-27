package Server.Controller;

import java.net.Socket;
import java.util.HashMap;


public class GameListener implements Runnable{
    private HashMap <String, Socket> connections;

    @Override
    public void run() {

    }
    public OpType deserializer(Socket connection){
        return null;
    }

}
