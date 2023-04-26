package Client.Controller;

import Client.View.View;
import Client.View.ViewFactory;
import Enumeration.OperationType;
import Messages.Client.InsertTilesMessage;
import Messages.Client.SelectedTilesMessage;
import Messages.Client.WriteChatMessage;
import Messages.ClientMessage;
import Exception.InvalidInputException;
import Utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

// TODO: 25/04/2023 :
// 1) check the syntax of the input and in case throw Exception.
// 2) adjust the view with method getInstant() that has to be implemented in the server.
// 3) implement pingMessage, addPlayerMessage.
// 4) check if is missing something and do tests.


public class Controller {
    private View view;
    private String playerID;

    public Controller() {
        // this.View = System.out.println("");
        // choose playerID
        System.out.println("Chose a nickname for the game: ");
        this.playerID = System.console().readLine();
    }

    public void changeView(String view) {
        setView(ViewFactory.getView(view));
    }

    public void setView(View view) {
        this.view = view;
    }

    public void inputReader() {
        // chose view
        while (true) {
            try {
                buildMessage(System.console().readLine());
            } catch (InvalidInputException e) {
                System.out.println("Input not valid!");
            }
        }
    }

    /**
     * Create a message with the information inserted by a player from input.
     * @param input String received from input.
     * @return Message that contains all the information that a player have inserted from input.
     */
    public ClientMessage buildMessage(String input) throws InvalidInputException {
        ClientMessage clientMessage;
        // command -body
        OperationType opType = checkInput(input);
        if (opType.equals(OperationType.CHANGEVIEW)) {
            // cv -CLI/GUI

            // yet to finish, need to transform String view to View ..........

            String view = input.split("-")[1];
            // clientMessage = new ChangeView(opType,playerID,view);
            // return clientMessage;

            //......................
        }
        if (opType.equals(OperationType.WRITEMESSAGE)) {
            // wm -message text
            int index = input.indexOf("-");
            // tmpOpType is a helper variable useful just for the creation of messageText
            String tmpOpType = input.substring(0, index).trim();
            String messageText = input.substring(index + 1).trim();
            clientMessage = new WriteChatMessage(playerID, messageText);
            return clientMessage;
        }
        if (opType.equals(OperationType.SELECTEDTILES)) {
            // st -x,y/x,y/x,y
            String coordinates = input.split("-")[1];

            List<Coordinates> selectedCoordinates = extractCoordinates(coordinates);
            clientMessage = new SelectedTilesMessage(playerID, selectedCoordinates);
            return clientMessage;
        }
        // it cannot be anything bit a insertTile because it's checked in assignOpType.
        // it -x,y,z/c
        String orderOfTiles = input.split("-")[1].split("/")[0];
        List<Integer> sorted = extractInteger(orderOfTiles);
        int column = Integer.parseInt(input.split("-")[1].split("/")[1]);
        clientMessage = new InsertTilesMessage(opType, playerID, sorted, column);
        return clientMessage;
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
        // st -x,y/x,y/x,y
        // it -x,y,z/c
        // wm -text of the message
        // cv -CLI/GUI
        String prefix = input.split("-")[0];
        if(!(prefix.equals("st ")) && !(prefix.equals("it ")) && !(prefix.equals("cv ")) && !(prefix.equals("wm ")))
            throw new InvalidInputException(input);

        OperationType opType = assignOpType(input.split(" ")[0]);

        // now I check the syntax after the command.

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
            // st x,y/x,y/x,y
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
}