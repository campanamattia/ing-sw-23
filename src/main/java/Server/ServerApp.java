package Server;


import Server.Network.Lobby.Lobby;
import Server.Network.Servers.SocketServer;
import Server.Network.Servers.RMIServer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerApp {
    public static Logger logger;
    private static final String serverSetting = "serverSetting.json";
    private static Lobby lobby;
    private static int socketPort = 0;
    private static int rmiPort = 0;


    public static void main(String[] args) {
        initLogger();
        setPort(args);
        initLobby();
        rmiServer();
        socketServer();
    }


    private static void initLogger(){
        logger = Logger.getLogger(ServerApp.class.getName());
        try {
            logger.addHandler(new FileHandler("src/main/resources/logger.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.setLevel(Level.ALL);
        logger.info("Starting ServerApp");
    }

    private static void setPort(String[] args){
        try{
            switch (args.length) {
                case 2 -> {
                    socketPort = Integer.parseInt(args[0]);
                    rmiPort = Integer.parseInt(args[1]);
                }
                case 1 -> {
                    socketPort = Integer.parseInt(args[0]);
                    rmiPort = rmiFromJSON();
                }
                default -> {
                    socketPort = socketFromJSON();
                    rmiPort = rmiFromJSON();
                }
            }
        }catch (RuntimeException e){
            logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
    }

    private static void initLobby() {
        try {
            lobby = new Lobby();
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
    }

    private static int socketFromJSON() throws RuntimeException{
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(serverSetting));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            return json.get("socketPort").getAsInt();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int rmiFromJSON() throws RuntimeException{
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(serverSetting));
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            return json.get("rmiPort").getAsInt();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void rmiServer(){
        try{
            new RMIServer().start(lobby, rmiPort);
        }catch (RemoteException | AlreadyBoundException e){
            logger.log(Level.SEVERE, e.toString());
            System.exit(-1);
        }
    }

    private static void socketServer() {
        new SocketServer().start(lobby, socketPort);
    }

}

