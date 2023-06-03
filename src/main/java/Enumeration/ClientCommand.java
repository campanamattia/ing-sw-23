package Enumeration;

import Client.View.Cli.CliColor;

public enum ClientCommand {
    CHAT("chat", "Open the chat"),
    HELP("help", "Display the list of available commands"),
    BACK("back", "Go back to the previous screen (living room)"),
    SELECT("select", "Select tiles using the format: " + CliColor.BOLDYELLOW + "st-(x1,y1)(x2,y2)(x3,y3)" + CliColor.RESET),
    INSERT("insert", "Insert tiles into a specific column using the format: " + CliColor.BOLDYELLOW + "it-tile1,tile2,tile3/column" + CliColor.RESET),
    WRITE(null, "everything else will be send as a message to the chat. Use the format: " + CliColor.BOLD + "message/to playerID" + CliColor.RESET + " or " + CliColor.BOLD + "message/to all" + CliColor.RESET);

    private final String command;
    private final String format;

    ClientCommand(String command, String format) {
        this.command = command;
        this.format = format;
    }

    public String toString() {
        return (command == null) ? format : "• " + CliColor.BOLDCYAN + command + ": " + CliColor.RESET + format;
    }

}
