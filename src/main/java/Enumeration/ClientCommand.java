package Enumeration;

/**
 * An enumeration representing client commands with their associated formats.
 */
public enum ClientCommand {
    CHAT("chat", "Open the chat"),
    HELP("help", "Display the list of available commands"),
    BACK("back", "Go back from the chat to the living room scene"),
    REFRESH("refresh", "Reload the living room scene"),
    SELECT("select", "Select tiles using the format: " + CliColor.BOLDYELLOW + "st-(x1,y1)(x2,y2)(x3,y3)" + CliColor.RESET),
    INSERT("insert", "Insert tiles into a specific column using the format: " + CliColor.BOLDYELLOW + "it-tile1,tile2,tile3/column" + CliColor.RESET),
    WRITE(null, "everything else will be send as a message to the chat. Use the format: " + CliColor.BOLD + "message/to playerID" + CliColor.RESET + " or " + CliColor.BOLD + "message/to all" + CliColor.RESET);

    private final String command;
    private final String format;

    /**
     * Constructs a client command with the specified command and format.
     *
     * @param command the command string.
     * @param format  the format description of the command.
     */
    ClientCommand(String command, String format) {
        this.command = command;
        this.format = format;
    }

    /**
     * Returns a string representation of the client command.
     * If the command is null, it returns the format description.
     * Otherwise, it returns the command followed by its format description.
     *
     * @return the string representation of the client command.
     */
    public String toString() {
        return (command == null) ? format : "• " + CliColor.BOLDCYAN + command + ": " + CliColor.RESET + format;
    }

}
