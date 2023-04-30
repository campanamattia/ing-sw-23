package Client.Controller;

import Client.ClientApp;
import Client.View.*;
import Client.Network.*;
import Enumeration.OperationType;
import Messages.Client.*;
import Exception.InvalidInputException;
import Messages.ClientMessage;
import Messages.ServerMessage;
import Utils.Coordinates;

import javax.swing.text.PlainView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// TODO: 27/04/2023
// 1) control that all input follows: Controller -> Network -> Server -> Network -> View. !!!!!!!!!!!!!
// 2) implement pingMessage, addPlayerMessage as settled with colleagues.
// 3) check if is missing something and do tests.
// 4) decide if error messages are sent to screen by controller or view.

public class Controller extends Thread{
    private View view;
    private final Network network;
    private String playerID;
    Scanner scanner = new Scanner(System.in);

    public Controller(View view, Network network,String playerID) {
        this.view = view;
        this.network = network;
        this.playerID = playerID;
    }

    public void start(){
        // while(Servers says okay){
        ClientMessage firstPlayer = new FirstPlayerMessage(playerID);
        try {
            Controller.this.network.sendMessage(firstPlayer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // now I receive the response from the server and chose if ask how many players.
        // ... serverMessage ...
        // scanner "how many player?"

        // forward setup to Network
        ClientMessage addPlayerMessage = new AddPlayerMessage(this.playerID);
        try {
            Controller.this.network.sendMessage(addPlayerMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // wait confirm from server
        // receive copyFromServer(ServerMessage)
        // wait for new turn

        Thread thread = new Thread(inputReader);
        thread.start();

        Thread thread1 = new Thread(pingThread);
        thread1.start();

        //}
    }

    Runnable pingThread = () -> {
        ClientMessage pingMessage = new PingMessage(playerID);
        try {
            Controller.this.network.sendMessage(pingMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // forward to Network
    };


    Runnable inputReader = () -> {
        while(true) {
            try {
                // generic input: opType-content
                String input = scanner.nextLine();
                OperationType opType = checkInput(input);
                String content = upLoadContent(input, opType);
                if (opType.equals(OperationType.CHANGEVIEW))
                    // ............ to view .................
                    changeView(content);
                else {
                    ClientMessage clientMessage;
                    clientMessage = buildMessage(opType, content);
                    Controller.this.network.sendMessage(clientMessage);
                }
            } catch (InvalidInputException e) {
                System.out.println("Input not valid!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };

    /**
     * Create a message with the information inserted by a player from input.
     * @param content String received from input.
     * @return Message that contains all the information that a player have inserted from input.
     */
    public ClientMessage buildMessage(OperationType opType, String content) throws InvalidInputException {
        ClientMessage clientMessage;

        if (opType.equals(OperationType.WRITEMESSAGE)) {
            // message text
            clientMessage = new WriteChatMessage(playerID, content);
            return clientMessage;
        }
        if (opType.equals(OperationType.SELECTEDTILES)) {
            // x,y/x,y/x,y
            List<Coordinates> selectedCoordinates = extractCoordinates(content);
            clientMessage = new SelectedTilesMessage(playerID, selectedCoordinates);
            return clientMessage;
        }
        // it cannot be anything bit a insertTile because it's checked in assignOpType.
        // x,y,z/c
        String orderOfTiles = content.split("/")[0];
        List<Integer> sorted = extractInteger(orderOfTiles);
        int column = Integer.parseInt(content.split("/")[1]);
        clientMessage = new InsertTilesMessage(opType, playerID, sorted, column);
        return clientMessage;
    }
    public void changeView(String view) {
        setView(ViewFactory.getView(view));
    }

    public void setView(View view) {
        this.view = view;
    }

    /**
     * Turn the command from stream to enumeration.
     *
     * @param input command inserted by the player.
     * @return Type of command inserted by a player in form of enumeration.
     * @throws InvalidInputException if the command chosen does not exist.
     */
    private OperationType assignOpType(String input) throws InvalidInputException {
        if (input.equals("cv")) {
            return OperationType.CHANGEVIEW;
        }
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

        if (opType.equals(OperationType.CHANGEVIEW)) {
            // cv -CLI/GUI
            if (!(input.equals("CLI")) && !(input.equals("GUI")))
                throw new InvalidInputException(input);
        }
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
    private String upLoadContent(String input,OperationType opType){
        if(opType.equals(OperationType.WRITEMESSAGE)){
            int index = input.indexOf("-");
            // tmpOpType is a helper variable useful just for the creation of messageText
            String tmpOpType = input.substring(0, index).trim();
            return input.substring(index + 1).trim();
        }else
            return input.split("-")[1];
    }
}