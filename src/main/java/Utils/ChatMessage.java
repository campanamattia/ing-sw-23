package Utils;

import Client.View.Cli.CliColor;

/**
 * Represents a message sent by a user in a chat room.
 */
public record ChatMessage(String from, String message, String to) {

    /**
     * Returns a string representation of the WriteChatMessage object in the format "From {from}:\n{message}".
     *
     * @return a string representation of the WriteChatMessage object
     */
    @Override
    public String toString() {
        return (to == null) ?
                CliColor.BOLD + "From " + from + " to ALL" + ":\n" + CliColor.RESET + message :
                CliColor.BOLD + "From " + from + " to " + CliColor.RESET + CliColor.BGREEN + "you" + CliColor.RESET + CliColor.BOLD +  ":\n" +CliColor.RESET + message;
    }
}
