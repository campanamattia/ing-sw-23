package Client.Controller;

import Client.Network.*;
import Client.View.View;
import Enumeration.OperationType;
import Exception.Player.InvalidInputException;
import Utils.Coordinates;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final View view;
    private String playerID;
    private Network network;


    public Controller(View view) {
        this.view = view;
    }

    public void doAction(String input) {

        // thread for afk

        if (input.equals("showChat")) {
            this.view.showChat();
            return;
        }
        if (input.equals("showGame")) {
            this.view.showGame();
            return;
        }
        if(input.equals("help") || input.equals("?")){
            this.view.showHelp();
            return;
        }


        try {

            OperationType operationType = checkInput(input);
            String content = upLoadContent(input, operationType);

            switch (operationType) {
                case WRITEMESSAGE -> network.writeChat(this.playerID, content, ""); // TODO: 24/05/2023
                case SELECTEDTILES -> {
                    List<Coordinates> selectCoordinate = extractCoordinates(content);
                    network.selectTiles(this.playerID, selectCoordinate);
                }
                case INSERTTILES -> {
                    String orderOfTiles = content.split("/")[0];
                    List<Integer> sorted = extractInteger(orderOfTiles);
                    int column = Integer.parseInt(content.split("/")[1]);
                    network.insertTiles(this.playerID, sorted, column);
                }
            }

        } catch (InvalidInputException e) {
            try {
                view.outcomeException(e);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the input inserted by a user is valid.
     *
     * @param input String received.
     * @return OperationType of the command in input.
     * @throws InvalidInputException if the input is not valid.
     */
    private OperationType checkInput(String input) throws InvalidInputException {

        // check syntax of the command.
        // st-(x,y)-(x,y)-(x,y)
        // it-x,y,z/c
        // wm-text of the message
        // cv-CLI/GUI
        String prefix = input.split("-")[0];
        if (!(prefix.equals("st")) && !(prefix.equals("it")) && !(prefix.equals("cv")) && !(prefix.equals("wm")))
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
                try {
                    Integer.parseInt(input.split("/")[1]);
                } catch (NumberFormatException e) {
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

    private String upLoadContent(String input, OperationType opType) {
        if (opType.equals(OperationType.WRITEMESSAGE)) {
            int index = input.indexOf("-");
            // tmpOpType is a helper variable useful just for the creation of messageText
            return input.substring(index + 1).trim();
        } else
            return input.split("-")[1];
    }

    /**
     * Receive coordinate in shape of String and turn them into list of coordinates.
     *
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
     * States whether the given address is valid or not.
     *
     * @param address the inserted IP address.
     * @return a boolean whose value is:
     * -{@code true} if the address is valid;
     * -{@code false} otherwise.
     */
    public boolean validateIP(String address) {
        String zeroTo255 = "([01]?\\d{1,2}|2[0-4]\\d|25[0-5])";
        String IP_REGEX = "^(" + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + ")$";
        return address.matches(IP_REGEX);
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}
