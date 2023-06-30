package Client.View.Cli;

import Utils.Coordinates;
import Utils.MockObjects.MockPlayer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Client.ClientApp.*;

/**
 * The LightController class handles the input commands and delegates the execution to the corresponding methods.
 * It interacts with the Cli view to display output messages and handle user input.
 */
public class LightController {

    private final Cli cli;

    /**
     * Constructs a new LightController instance.
     */
    public LightController(){
        this.cli = (Cli) view;
    }

    /**
     * Processes the input command and delegates the execution to the corresponding method.
     *
     * @param input The input command to be processed.
     */
    public void elaborate(String input) {
        String[] split = input.split("-");
        String command = split[0];
        switch (command.trim()) {
            case "chat" -> this.cli.showChat();
            case "help" -> this.cli.showHelp();
            case "back", "refresh" -> this.cli.showAll();
            case "st"   -> executorService.execute(() -> selectTiles(split));
            case "it"   -> executorService.execute(() -> insertTiles(split));
            default     -> executorService.execute(() -> writeChat(input));
        }
    }

    /**
     * Executes the selectTiles command.
     *
     * @param split The array of split command parts.
     */
    private void selectTiles(String[] split) {
        if (split.length != 2){
            this.cli.printError("You must follow the correct format: st-coordinates");
            return;
        }

        // Check if the data is in the correct format
        List<Coordinates> coordinates = checkSelectFormat(split[1]);
        if (coordinates == null) {
            cli.printError("You can't select the same tile twice");
            return;
        }

        // Send the command to the server
        try {
            network.selectTiles(localPlayer, coordinates);
        } catch (RemoteException e) {
            this.cli.printError(e.getMessage());
        }
    }

    /**
     * Checks if the select tiles data is in the correct format.
     *
     * @param data The data string to be checked.
     * @return The list of parsed coordinates if the format is correct, null otherwise.
     */
    private List<Coordinates> checkSelectFormat(String data) {
        List<Coordinates> coordinates = new ArrayList<>();

        // Check if the data is in the correct format
        String pattern = "\\(\\d,\\d\\)";
        for (int i = 0; i < 3; i++) {
            if (data.matches(pattern))
                break;
            pattern += "\\(\\d,\\d\\)";
        }
        if (!data.matches(pattern)) {
            this.cli.printError("You must follow the correct format: (x1,y1)(x2,y2)(x3,y3)");
            return null;
        }

        // Split the data into pairs of coordinates
        String[] sections = data.split("\\)\\(");

        // Process each coordinate pair
        for (String section : sections) {
            // Split the pair into x and y values
            String[] pair = section.split(",");

            // Extract x and y values
            int x;
            int y;
            try {
                x = Integer.parseInt(pair[0].replaceAll("[()]", ""));
                y = Integer.parseInt(pair[1].replaceAll("[()]", ""));
            } catch (NumberFormatException e) {
                this.cli.printError("You must insert numbers as coordinates");
                return null;
            }

            if (coordinates.contains(new Coordinates(x, y))) {
                return null;
            } else coordinates.add(new Coordinates(x, y));
        }
        return coordinates;
    }

    /**
     * Executes the insert tiles command.
     *
     * @param split The array of split command parts.
     */
    private void insertTiles(String[] split) {
        if (split.length != 2){
            this.cli.printError("You must follow the correct format: it-tiles/column");
            return;
        }

        String[] data = split[1].split("/");
        if (data.length != 2) this.cli.printError("Invalid command");

        // Check if the data is in the correct format
        List<Integer> tiles = checkInsertFormat(data[0]);
        if (tiles == null)
            return;

        // Check if the column is in the correct format
        int column = checkColumn(data[1]);
        if (column == -1)
            return;

        // Send the command to the server
        try {
            network.insertTiles(localPlayer, tiles, column);
        } catch (RemoteException e) {
            this.cli.printError(e.getMessage());
        }
    }

    /**
     * Checks if the column data is in the correct format and converts it to the corresponding numeric value.
     *
     * @param data The column data to be checked.
     * @return The numeric value of the column if it is valid (between A and E), -1 otherwise.
     */
    private int checkColumn(String data) {
        if (data.compareTo("A") >= 0 && data.compareTo("E") <= 0)
            return data.charAt(0) - 'A';
        this.cli.printError("You must insert a valid column (A-E)");
        return -1;
    }

    /**
     * Checks if the insert tiles data is in the correct format.
     *
     * @param data The data string to be checked.
     * @return The list of parsed tiles if the format is correct, null otherwise.
     */
    private List<Integer> checkInsertFormat(String data) {
        List<Integer> tiles = new ArrayList<>();

        // Check if the data is in the correct format
        String pattern = "\\d";
        for (int i = 0; i < 3; i++) {
            if (data.matches(pattern))
                break;
            pattern += ",\\d";
        }
        if (!data.matches(pattern)) {
            this.cli.printError("You must follow the correct format for the order: 1,2,3");
            return null;
        }

        String [] split = data.split(",");
        // Process each tile
        for (String s : split) {
            // Extract the tile
            int tile;
            try {
                tile = Integer.parseInt(String.valueOf(s));
            } catch (NumberFormatException e) {
                this.cli.printError("You must insert numbers to order the tiles correctly");
                return null;
            }

            if (tiles.contains(tile)) {
                this.cli.printError("You can't insert the same tile twice");
                return null;
            } else tiles.add(tile);
        }
        return tiles;
    }

    /**
     * Executes the write chat command.
     *
     * @param input The input command to be processed.
     */
    private void writeChat(String input) {
        String[] data = input.split("/to");
        data = Arrays.stream(data).map(String::trim).toArray(String[]::new);

        if (data.length != 2) {
            this.cli.printError("You must follow the correct format: message/to playerID");
            return;
        }

        if (data[1].trim().equalsIgnoreCase("all")) {
            try {
                network.writeChat(localPlayer, data[0], null);
            } catch (RemoteException e) {
                this.cli.printError(e.getMessage());
            }
            return;
        }

        if (localPlayer.equalsIgnoreCase(data[1].trim())) {
            this.cli.printError("You can't send a message to yourself");
            return;
        }

        for (MockPlayer player : view.getMockModel().getMockPlayers()) {
            if (player.getPlayerID().equalsIgnoreCase(data[1].trim())) {
                try {
                    network.writeChat(localPlayer, data[0], player.getPlayerID());
                } catch (RemoteException e) {
                    this.cli.printError(e.getMessage());
                }
                return;
            }
        }
        this.cli.printError("Player not found");
    }
}
