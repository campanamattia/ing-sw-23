package Utils;

import Enumeration.MessageType;
import Enumeration.OperationType;
import Messages.Client.AddPlayerMessage;
import Messages.Server.*;
import Messages.ServerMessage;
import com.google.gson.Gson;

import static Enumeration.OperationType.SELECTEDTILES;

public class ServerMessageFactory {

    public static ServerMessage getServerMessage(String type, String line){
        Gson gson =  new Gson();
        for (MessageType messageType: MessageType.values()) {
            if (messageType.toString().equals(type)) {
                return gson.fromJson(line, getServerMessageClass(messageType));
            }
        }
        throw new IllegalArgumentException("Operation not found");
    }

    private static Class<? extends ServerMessage> getServerMessageClass(MessageType messageType) {
        return switch (messageType) {
            case PONG ->  PongMessage.class;
            case ERROR -> ErrorMessage.class;
            case SELECTEDTILES -> SelectedTilesMessage.class; //da capire per le altre
            case INSERTEDTILES -> InsertedTilesMessage.class;
            case ADDEDPLAYER -> AddedPlayerMessage.class;
            case ENDGAME -> EndGameMessage.class;
            case CHATROOM -> ChatRoomMessage.class;
            case SCREENSHOT -> StatusMessage.class;
            default -> null;
        };
    }

}

