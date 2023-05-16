package Utils;

import Enumeration.OperationType;
import Messages.Client.GameController.InsertTilesMessage;
import Messages.Client.Lobby.PingMessage;
import Messages.Client.GameController.SelectedTilesMessage;
import Messages.Client.GameController.WriteChatMessage;
import Messages.ClientMessage;
import com.google.gson.Gson;

public class ClientMessageFactory {

    public static ClientMessage getClientMessage(String type, String line){
        Gson gson = new Gson();
        for (OperationType operationType: OperationType.values()) {
            if (operationType.toString().equals(type)) {
                return gson.fromJson(line, getClientMessageClass(operationType));
            }
        }
        throw new IllegalArgumentException("OperationType not found");
    }

    private static Class<? extends ClientMessage> getClientMessageClass(OperationType operationType) {
        return switch (operationType) {
            case INSERTTILES -> InsertTilesMessage.class;
            case SELECTEDTILES -> SelectedTilesMessage.class;
            case WRITEMESSAGE -> WriteChatMessage.class;
            case PING -> PingMessage.class;
            default -> null;
        };
    }
}
