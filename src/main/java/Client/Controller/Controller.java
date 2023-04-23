package Client.Controller;

import Client.View.View;
import Client.View.ViewFactory;
import Enumeration.OperationType;
import Messages.ClientMessage;
import Exception.InvalidInputException;

public class Controller {
    private View view;

    public Controller(View view) {
        this.view = view;
    }

    public void changeView(String view){
        setView(ViewFactory.getView(view));
    }

    private void setView(View view) {
        this.view = view;
    }

    /**
     * Create a message with the information inserted by a player from input.
     * @param input String received from input.
     * @return Message that contains all the information that a player have inserted from input.
     */
    private ClientMessage buildMessage(String input) throws InvalidInputException {
        ClientMessage clientMessage = null;
        OperationType opType = assignOpType(input.split(" ")[0]);
        clientMessage.setOperationType(opType);
        String content = input.split("-")[1];
        return null;
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
}
