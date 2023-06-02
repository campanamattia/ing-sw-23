package Client.View.Cli;

import Utils.Coordinates;
import Utils.MockObjects.MockPlayer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Client.ClientApp.*;

public class LightController {
    private final String playerID;

    public LightController(String playerID) {
        this.playerID = playerID;
    }

    public void elaborate(String input) {
        String[] split = input.split("-");
        String command = split[0];
        switch (command) {
            case "chat" -> view.showChat();
            case "help" -> view.showHelp();
            case "back", "quit" -> view.showStatus();
            case "st" -> executorService.execute(() -> selectTiles(split));
            case "it" -> executorService.execute(() -> insertTiles(split));
            case "wc" -> executorService.execute(() -> writeChat(split));
            default -> view.printError("INVALID COMMAND");
        }
    }

    private void selectTiles(String[] split) {
        if (split.length != 2){
            view.printError("You must follow the correct format: st-coordinates");
            return;
        }

        // Check if the data is in the correct format
        List<Coordinates> coordinates = checkSelectFormat(split[1]);
        if (coordinates == null)
            return;

        // Send the command to the server
        try {
            network.selectTiles(playerID, coordinates);
        } catch (RemoteException e) {
            view.printError(e.getMessage());
        }
    }

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
            view.printError("You must follow the correct format: (x1,y1)(x2,y2)(x3,y3)");
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
                view.printError("You must insert numbers as coordinates");
                return null;
            }

            if (coordinates.contains(new Coordinates(x, y))) {
                return null;
            } else coordinates.add(new Coordinates(x, y));
        }
        return coordinates;
    }

    private void insertTiles(String[] split) {
        if (split.length != 2){
            view.printError("You must follow the correct format: it-tiles/column");
            return;
        }

        String[] data = split[1].split("/");
        if (data.length != 2) view.printError("Invalid command");

        // Check if the data is in the correct format
        List<Integer> tiles = checkInsertFormat(data[0]);
        if (tiles == null)
            return;

        // Check if the column is in the correct format
        int column = checkColumn(data[1]);
        if (column != -1)
            return;

        // Send the command to the server
        try {
            network.insertTiles(playerID, tiles, column);
        } catch (RemoteException e) {
            view.printError(e.getMessage());
        }
    }

    private int checkColumn(String data) {
        if (data.matches("[A-E]"))
            return data.charAt(0) - 'A';
        return -1;
    }

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
            view.printError("You must follow the correct format for the order: 1,2,3");
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
                view.printError("You must insert numbers to order the tiles correctly");
                return null;
            }

            if (tiles.contains(tile)) {
                view.printError("You can't insert the same tile twice");
                return null;
            } else tiles.add(tile);
        }
        return tiles;
    }

    private void writeChat(String[] split) {
        String[] data = split[1].split("/to");
        data = Arrays.stream(data).map(String::trim).toArray(String[]::new);
        if (data.length != 2){
            view.printError("You must follow the correct format: wc-message/to playerID");
            return;
        }
        if(data[1].equalsIgnoreCase("ALL"))
            data[1] = null;
        else {
            for (MockPlayer player : view.getMockModel().getMockPlayers())
                if (player.getPlayerID().equalsIgnoreCase(data[1])){
                    try {
                        network.writeChat(playerID, data[0], player.getPlayerID());
                    } catch (RemoteException e) {
                        view.printError(e.getMessage());
                    }
                    return;
                }
        }
        view.printError("Player not found");
    }
}
