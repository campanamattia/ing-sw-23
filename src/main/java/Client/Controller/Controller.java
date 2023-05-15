package Client.Controller;

import Client.Network.*;
import Client.View.View;
import Enumeration.OperationType;
import Exception.InvalidInputException;
import Server.ServerApp;
import Utils.Coordinates;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Controller{
    private View view;
    private String playerID;
    private Network network;
    private String ipAddress;
    private int port;

    public Controller(View view) {
        this.view = view;
    }

    public void start(){
        this.network = view.askConnection();
        port = view.askPort();
        ipAddress = view.askIpAddress();
        network.init(port,ipAddress);
        try {
            view.askPlayerID();
        } catch (RemoteException e) {
            ServerApp.logger.log(Leve);
        }
        // make askPlayerID void
        // add a method to set playerID
    }

    // add method are you still here ? in do action !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public void doAction(String input) throws InvalidInputException {
        OperationType operationType = checkInput(input);
        String content = upLoadContent(input,operationType);
        switch (operationType){
            case WRITEMESSAGE -> {
                network.writeChat(this.playerID,content);
            }
            case SELECTEDTILES -> {
                List<Coordinates> selectCoordinate = extractCoordinates(content);
                network.selectTiles(playerID, selectCoordinate);
            }
            case INSERTTILES -> {
                String orderOfTiles = content.split("/")[0];
                List<Integer> sorted = extractInteger(orderOfTiles);
                int column = Integer.parseInt(content.split("/")[1]);
                network.insertTiles(playerID, sorted, column);
            }
        }
    }

    public void addPlayer(String playerID){
        this.playerID = playerID;
        network.addPlayer(this.playerID); // this method will go into set player id
    }


    /**
     * Checks if the input inserted by a user is valid.
     * @param input String received.
     * @throws InvalidInputException if the input is not valid.
     * @return OperationType of the command in input.
     */
    private OperationType checkInput(String input) throws InvalidInputException {

        // check syntax of the command.
        // st-x,y/x,y/x,y
        // it-x,y,z/c
        // wm-text of the message
        // cv-CLI/GUI
        String prefix = input.split("-")[0];
        if(!(prefix.equals("st")) && !(prefix.equals("it")) && !(prefix.equals("cv")) && !(prefix.equals("wm")))
            throw new InvalidInputException(input);

        OperationType opType = assignOpType(input.split("-")[0]);

        // now that I checked if prefix is correct, input turn into the following part of th "-".
        input = input.split("-")[1];

        if (opType.equals(OperationType.INSERTTILES)) {
            // it -x,y,z/c
            String[] parts = input.split("/");
            if (parts.length != 2) {
                throw new InvalidInputException(input);
            }
            for (String p : parts) {
                String[] coordinate = p.split(",");
                if (coordinate.length > 3 || coordinate.length == 0) {
                    throw new InvalidInputException(input);
                }
                try {
                    for (String s : coordinate) {
                        Integer.parseInt(s);
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidInputException(input);
                }
                try{
                    Integer.parseInt(input.split("/")[1]);
                }catch (NumberFormatException e){
                    throw new InvalidInputException(input);
                }
            }
        }
        if (opType.equals(OperationType.SELECTEDTILES)) {
            // st -x,y/x,y/x,y
            String[] parts = input.split("/");
            if (parts.length > 3 || parts.length == 0) {
                throw new InvalidInputException(input);
            }
            for (String p : parts) {
                String[] coordinate = p.split(",");
                if (coordinate.length != 2) {
                    throw new InvalidInputException(input);
                }
                try {
                    Integer.parseInt(coordinate[0]);
                    Integer.parseInt(coordinate[1]);
                } catch (NumberFormatException e) {
                    throw new InvalidInputException(input);
                }
            }
        }
        return opType;
    }

    /**
     * Turn the command from stream to enumeration.
     *
     * @param input command inserted by the player.
     * @return Type of command inserted by a player in form of enumeration.
     * @throws InvalidInputException if the command chosen does not exist.
     */
    private OperationType assignOpType(String input) throws InvalidInputException {
        if (input.equals("wm")) {
            return OperationType.WRITEMESSAGE;
        }
        if (input.equals("st")) {
            return OperationType.SELECTEDTILES;
        }
        if (input.equals("it")) {
            return OperationType.INSERTTILES;
        }
        throw new InvalidInputException(input);
    }

    private String upLoadContent(String input,OperationType opType){
        if(opType.equals(OperationType.WRITEMESSAGE)){
            int index = input.indexOf("-");
            // tmpOpType is a helper variable useful just for the creation of messageText
            String tmpOpType = input.substring(0, index).trim();
            return input.substring(index + 1).trim();
        }else
            return input.split("-")[1];
    }

    /**
     * Receive coordinate in shape of String and turn them into list of coordinates.
     * @param input Receive coordinate in shape of String.
     * @return list of coordinates.
     */
    private List<Coordinates> extractCoordinates(String input) {
        String[] tmp = input.split("/");
        List<Coordinates> coordinates = new ArrayList<>();
        Coordinates tmpCoordinates;
        for (int i = 0; i < tmp.length; i++) {
            tmpCoordinates = new Coordinates(Integer.parseInt(input.split("/")[i].split(";")[0]), Integer.parseInt(input.split("/")[i].split(";")[1]));
            coordinates.add(tmpCoordinates);
        }
        return coordinates;
    }

    /**
     * Turn input from String into a list of int.
     *
     * @param input input of number in shape of string.
     * @return list of int.
     */
    private List<Integer> extractInteger(String input) {
        String[] tmp = input.split(",");
        List<Integer> orderOfTiles = new ArrayList<>();
        for (String s : tmp) {
            orderOfTiles.add(Integer.parseInt(s));
        }
        return orderOfTiles;
    }

}