package Utils;

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
        return (to == null) ? "From " + from + " to ALL" + ":\n" + message : "From " + from + " to " + to + ":\n" + message;
    }
}
