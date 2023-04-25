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

public class Controller {
    private View view;

    // ...........................
    // to review if I need a list of playerID
    // ...........................
    private String playerID;

    public Controller() {
        // this.View = System.out.println("");
        // choose playerID
        System.out.println("Chose a nickname for the game: ");
        this.playerID = System.console().readLine();
    }

    public void changeView(String view){
        setView(ViewFactory.getView(view));
    }

    public void setView(View view) {
        this.view = view;
    }

    public void inputReader() {
        // chose view
        while(true){
            try {
                buildMessage(System.console().readLine());
            }catch (InvalidInputException e){
                System.out.println("Input non valido!");
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
        OperationType opType = assignOpType(input.split(" ")[0]);
        if(opType.equals(OperationType.CHANGEVIEW)){
            if( !(input.equals("CLI")) && !(input.equals("GUI")) )
                throw new InvalidInputException(input);
            // cv -CLI/GUI

            // yet to finish, need to transform String view to View ..........

            String view = input.split("-")[1];
            // clientMessage = new ChangeView(opType,playerID,view);
            // return clientMessage;

            //......................
        }
        if(opType.equals(OperationType.WRITEMESSAGE)){
            // wm -message text
            //................
            // corner case " this is a-text
            // ..................
            String messageText = input.split("-")[1];
            clientMessage = new WriteChatMessage(playerID,messageText);
            return clientMessage;
        }
        if(opType.equals(OperationType.SELECTEDTILES)){
            // st -x,y/x,y/x,y
            String coordinates = input.split("-")[1];
            List<Coordinates> selectedCoordinates = extractCoordinates(coordinates);
            clientMessage = new SelectedTilesMessage(playerID,selectedCoordinates);
            return clientMessage;
        }
        // it cannot be anything bit a insertTile because it's checked in assignOpType.
        // it -x,y,z/c
        String orderOfTiles = input.split("-")[1].split("/")[0];
        List<Integer> sorted = extractInteger(orderOfTiles);
        int column = Integer.parseInt(input.split("-")[1].split("/")[1]);
        clientMessage = new InsertTilesMessage(opType,playerID,sorted,column);
        return clientMessage;
    }

    /**
     * Turn the command from stream to enumeration.
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
    private List<Coordinates> extractCoordinates(String input){
        String[] tmp = input.split("/");
        List<Coordinates> coordinates = new ArrayList<>();
        Coordinates tmpCoordinates;
        for(int i=0;i<tmp.length;i++){
            tmpCoordinates = new Coordinates(Integer.parseInt(input.split("/")[i].split(";")[0]),Integer.parseInt(input.split("/")[i].split(";")[1]));
            coordinates.add(tmpCoordinates);
        }
        return coordinates;
    }

    /**
     * Turn input from String into a list of int.
     * @param input input of number in shape of string.
     * @return list of int.
     */
    private List<Integer> extractInteger(String input){
        String[] tmp = input.split(",");
        List<Integer> orderOfTiles = new ArrayList<>();
        for (String s : tmp) {
            orderOfTiles.add(Integer.parseInt(s));
        }
        return orderOfTiles;
    }
}