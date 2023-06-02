package Utils;

import Client.View.Cli.CliColor;

import java.io.Serializable;

/**
 * Represents a message sent by a user in a chat room.
 */
public record ChatMessage(String from, String message, String to) implements Serializable {

    /**
     * Returns a string representation of the WriteChatMessage object in the format "From {from}:\n{message}".
     *
     * @return a string representation of the WriteChatMessage object
     */
    @Override
    public String toString() {
        return (to == null) ?
                CliColor.BOLD + "From " + from + " to "+ CliColor.RESET + CliColor.BOLDGREEN + "ALL" + CliColor.RESET + CliColor.BOLD + ":\n" + CliColor.RESET + message :
                CliColor.BOLD + "From " + from + " to " + CliColor.RESET + CliColor.BOLDGREEN + "you" + CliColor.RESET + CliColor.BOLD +  ":\n" +CliColor.RESET + message;
    }
}
