package Client.View.Cli;

import Client.Network.Network;
import Client.Network.NetworkFactory;
import Client.View.View;
import Utils.*;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;
import org.jetbrains.annotations.NotNull;
import Enumeration.TurnPhase;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import static Client.ClientApp.network;

public class Cli extends View {
    private LightController controller;
    private final Scanner scanner = new Scanner(System.in);
    private Thread inputThread;

    public Cli() throws RemoteException {
        super();
        mockModel = new MockModel();
        showTitle();
    }

    @Override
    public void updateBoard(MockBoard mockBoard) {
        this.mockModel.setMockBoard(mockBoard);
    }

    @Override
    public void updateCommonGoal(MockCommonGoal mockCommonGoal) {
        this.mockModel.update(mockCommonGoal);
    }

    @Override
    public void updatePlayer(MockPlayer mockPlayer) {
        this.mockModel.update(mockPlayer);
    }

    @Override
    public void updateChat(ChatMessage message) {
        if (message.to() == null || message.to().equals(mockModel.getLocalPlayer())) {
            this.mockModel.addMessage(message);
            System.out.println(CliColor.BOLD + "\rNew Message" + CliColor.RESET);
        }
    }

    public void start() {
        int port;
        String address;

        try {
            network = askConnection();
        } catch (RemoteException e) {
            printError("ERROR: " + e.getMessage());
            System.exit(-1);
        }
        address = askServerAddress();
        port = askServerPort();
        Thread connection = new Thread(() -> network.init(address, port));
        connection.start();
    }

    public Network askConnection() throws RemoteException {
        String input;
        System.out.print(CliColor.BOLD + "To start select a connection protocol between 'SOCKET' or 'RMI': " + CliColor.RESET);

        input = scanner.nextLine();

        while (!input.equalsIgnoreCase("SOCKET") && !input.equalsIgnoreCase("RMI")) {
            printError("ERROR: you type something wrong, please enter 'SOCKET' or 'RMI'");
            input = scanner.nextLine();
        }

        printMessage("Good! You are going to create a " + input.toLowerCase() + " connection.");

        return NetworkFactory.instanceNetwork(input, this);
    }

    @Override
    public void askLobbySize() throws RemoteException {
        int playerNumber;
        String input;

        while (true) {
            try {
                System.out.print(CliColor.BOLD + "Please insert the numbers of players (insert a number between 2 and 4): " + CliColor.RESET);
                input = scanner.nextLine();
                playerNumber = Integer.parseInt(input);
                if (playerNumber >= 2 && playerNumber <= 4) {
                    break;
                }
                System.out.println(CliColor.RED + "ERROR: the game can start only with 2, 3 or 4 players." + CliColor.RESET);
            } catch (NumberFormatException exception) {
                System.out.println(CliColor.RED + "ERROR: don't insert letter, only number" + CliColor.RESET);
            }
        }

        printMessage("You are going to create a new Game, wait for the others players");

        network.setLobbySize(mockModel.getLocalPlayer(), mockModel.getLobbyID(), playerNumber);
    }

    public String askServerAddress() {
        final String DEFAULT_ADDRESS = "127.0.0.1";

        System.out.print(CliColor.BOLD + "Please enter the server address. " + CliColor.RESET);
        do {
            System.out.print(CliColor.BOLD + "\nInsert 'localhost' for the default value (" + DEFAULT_ADDRESS + "): " + CliColor.RESET);
            String address = scanner.nextLine();

            if (address.equalsIgnoreCase("localhost") || address.equals("d")) {
                return DEFAULT_ADDRESS;
            } else if (validateIP(address)) {
                return address;
            } else {
                printError("ERROR: Invalid address! (remember the syntax xxx.xxx.xxx.xxx)");
                System.out.println(" Try again.");
            }
        } while (true);
    }

    /**
     * States whether the given address is valid or not.
     *
     * @param address the inserted IP address.
     * @return a boolean whose value is:
     * -{@code true} if the address is valid;
     * -{@code false} otherwise.
     */
    private boolean validateIP(String address) {
        String zeroTo255 = "([01]?\\d{1,2}|2[0-4]\\d|25[0-5])";
        String IP_REGEX = "^(" + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + ")$";
        return address.matches(IP_REGEX);
    }

    public int askServerPort() {
        final int DEFAULT_SOCKET_PORT = 50000;
        final int DEFAULT_RMI_PORT = 50001;
        final int MIN_PORT = 1024;
        final int MAX_PORT = 65535;


        while (true) {
            System.out.print(CliColor.BOLD + "Select a valid port between [" + MIN_PORT + ", " + MAX_PORT + "]. ");
            System.out.print("\nInsert 'default' for the default value [for SOCKET (" + DEFAULT_SOCKET_PORT + "); for RMI (" + DEFAULT_RMI_PORT + ")]: " + CliColor.RESET);

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("default") || input.equals("d")) {
                return -1;
            } else {
                try {
                    int port = Integer.parseInt(input);
                    if (MIN_PORT <= port && port <= MAX_PORT) {
                        return port;
                    } else {
                        printError("ERROR: MIN PORT = " + MIN_PORT + ", MAX PORT = " + MAX_PORT + ".");
                        System.out.println(" Try again.");
                    }
                } catch (NumberFormatException e) {
                    printError("ERROR: Please insert only numbers or 'default'.");
                    System.out.println("Try again.");
                }
            }
        }
    }

    @Override
    public void askPlayerInfo(List<Map<String, String>> lobbyInfo) throws RemoteException {
        String inputLobby;
        String inputName;

        if (lobbyInfo != null) {
            System.out.println("Here you can find the lobbies or games with the players logged. Write an ID for the lobby/game; if it doesn't match with others, a new lobby will be instantiated.");
            for (String object : lobbyInfo.get(0).keySet())
                System.out.println("LobbyID: " + object + "\tWaiting Room: " + lobbyInfo.get(0).get(object));
            for (String object : lobbyInfo.get(1).keySet())
                System.out.println("GameID: " + object + "\tPlayers Online: " + lobbyInfo.get(1).get(object));
        } else System.out.println("There are no lobby or games: create a new one");


        while (true) {
            System.out.print(CliColor.BOLD + "\nInsert a lobby ID: " + CliColor.RESET);
            String input = scanner.nextLine();
            if (!input.isBlank()) {
                inputLobby = input;
                break;
            } else
                printError("ERROR: you type something wrong, lobby can't be empty");
        }

        while (true) {
            System.out.print(CliColor.BOLD + "Insert your Nickname: " + CliColor.RESET);
            String input = scanner.nextLine();
            if (!input.isBlank()) {
                inputName = input;
                break;
            } else
                printError("ERROR: you type something wrong, nickname can't be empty");
        }

        network.login(inputName, inputLobby, this, network);
    }


    @Override
    public void showBoard() {
        Cell[][] board = mockModel.getMockBoard().getBoard();
        int numberPlayer = mockModel.getMockPlayers().size();
        MockCommonGoal commonGoal1 = mockModel.getMockCommonGoal().get(0);
        MockCommonGoal commonGoal2 = mockModel.getMockCommonGoal().get(1);

        String[] parole1 = commonGoal1.getDescription().split(" ");
        String[] parole2 = commonGoal2.getDescription().split(" ");

        List<String> subString1 = new LinkedList<>(subString(parole1));
        while (subString1.size() <= 3) {
            subString1.add(null);
        }

        List<String> subString2 = new LinkedList<>();
        while (subString2.size() <= 3) {
            subString2.add(null);
        }
        subString2.addAll(subString(parole2));
        while (subString2.size() <= 8) {
            subString2.add(null);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" \t");
        if (numberPlayer == 2) for (int i = 0; i <= 6; i++)
            stringBuilder.append("  ").append(i).append("  ");
        else for (int i = 0; i <= 8; i++)
            stringBuilder.append("  ").append(i).append("  ");
        System.out.print(stringBuilder.append("\t|\t "));


        System.out.println(CliColor.BOLD + "COMMON GOAL" + CliColor.RESET);

        for (int i = 0; i < board.length; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getStatus() && board[i][j].getTile() != null) {
                    String colorString = board[i][j].getTile().getColor().getCode();
                    System.out.print(CliColor.BBLACK + "|" + colorString + i + "," + j + CliColor.BBLACK + "|" + CliColor.RESET);
                } else {
                    System.out.print(CliColor.BBLACK + "|   |" + CliColor.RESET); //print empty black space
                }
            }
            System.out.print("\t| ");

            //print CommonGoal
            if (i <= 2) {
                if (i == 0) {
                    System.out.print("[" + CliColor.BBLACK + " " + commonGoal1.getScoringToken().get(commonGoal1.getScoringToken().size()-1) + " " + CliColor.RESET + "] - ");
                }
                if (subString1.get(i) != null) System.out.print(subString1.get(i));
                else System.out.print("");
            }


            if (i >= 4 && i <= 6) {
                if (i == 4) {
                    System.out.print("[" + CliColor.BRED + " " + commonGoal2.getScoringToken().pop() + " " + CliColor.RESET + "] - ");
                }

                if (subString2.get(i) != null) System.out.print(subString2.get(i));
                else System.out.print("");
            }

            System.out.println();
        }
        System.out.println("\n");
    }

    private List<String> subString(String[] words) {
        List<String> subString = new LinkedList<>();
        StringBuilder sb1 = new StringBuilder();
        int maxLength = 85;

        for (String word : words) {
            if (word.length() > maxLength) {
                if (sb1.length() > 0) {
                    subString.add(sb1.toString().trim());
                    sb1.setLength(0);
                }
                subString.add(word);
            } else if (sb1.length() + word.length() <= maxLength) {
                sb1.append(word).append(" ");
                if (sb1.length() > maxLength) {
                    subString.add(sb1.toString().trim());
                    sb1.setLength(0);
                }
            } else {
                subString.add(sb1.toString().trim());
                sb1.setLength(0);
                sb1.append(word).append(" ");
            }
        }
        if (sb1.length() > 0) {
            subString.add(sb1.toString().trim());
        }
        return subString;
    }

    @Override
    public void showChat() {
        for (ChatMessage message : mockModel.getChat()) {
            System.out.println(message);
        }
    }

    @Override
    public void showStatus() {
        if (mockModel.getCurrentPlayer().equals(mockModel.getLocalPlayer())) {
            System.out.println(CliColor.BOLD + "It's your turn. " + mockModel.getTurnPhase() + " For more help type 'help'" + CliColor.RESET);
        } else {
            System.out.println(CliColor.BOLD + "It's NOT your turn. Wait for others player. For help type 'help'" + CliColor.RESET);
        }
    }


    public void showTitle() {
        System.out.print(CliColor.BOLDYELLOW);
        System.out.println("""
                 ✹ ｡  .  ･ . ∴ * ███╗   ███╗██╗   ██╗    ██████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗. 　･ ∴　　｡ 　
                ｡    ✦    *      ████╗ ████║╚██╗ ██╔╝   ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝ ∴⋆  ˚  *   .
                  ∴   *  ｡ .  ✹  ██╔████╔██║ ╚████╔╝    ╚█████╗ ███████║█████╗  ██║     █████╗  ██║█████╗   ｡ ·　 ✦   *
                 .   ･  *   ｡  ∴ ██║╚██╔╝██║  ╚██╔╝      ╚═══██╗██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝　   ✹  ｡   ·  ✧
                  ･  .   ✦     * ██║ ╚═╝ ██║   ██║      ██████╔╝██║  ██║███████╗███████╗██║     ██║███████╗ ✦ ∴ 　･ ｡· ∴
                  ✹   ｡ ∴.  ･   .╚═╝     ╚═╝   ╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝ ･　 *　　✹　 ˚""" + CliColor.RESET);

        System.out.println("Complete rules are available here: " + CliColor.BOLDPINK + "https://www.craniocreations.it/prodotto/my-shelfie\n" + CliColor.RESET);
    }


    public void showTile(@NotNull List<Tile> tiles) {
        System.out.print("\t");
        for (int i = 0; i < tiles.size(); i++) {
            System.out.print(tiles.get(i).getColor().getCode() + "|" + (i + 1) + "|");
            System.out.print(CliColor.RESET + "   ");
        }
        System.out.println();
    }

    // TODO: 16/05/23
    @Override
    public void endGame(List<Rank> classification) {

    }

    @Override
    public void crashedPlayer(String crashedPlayer) throws RemoteException {
        System.out.println(crashedPlayer + "is crashed but the game still continue!!");
    }

    @Override
    public void reloadPlayer(String reloadPlayer) throws RemoteException {
        System.out.println(reloadPlayer + "rejoined the game");
    }


    public void showShelves() {
        int numColumn = 5;
        int numRow = 6;
        int numPlayer = mockModel.getMockPlayers().size();

        for (int i = 0; i < numRow; i++) {
            System.out.print(" \t");
            for (int k = 0; k < numPlayer; k++) {
                for (int j = 0; j < numColumn; j++) {

                    Tile[][] shelf = mockModel.getMockPlayers().get(k).getShelf();
                    Tile[][] privateGoal = mockModel.getMockPlayers().get(k).getPersonalGoal();
                    String colorString = (shelf[i][j] != null) ? shelf[i][j].getColor().getCode() : CliColor.BBLACK.toString();
                    String colorBar;

                    if (mockModel.getLocalPlayer().equals(mockModel.getMockPlayers().get(k).getPlayerID())) {
                        colorBar = (privateGoal[i][j] != null) ? privateGoal[i][j].getColor().getCode() : CliColor.BBLACK.toString();
                    } else {
                        colorBar = CliColor.BBLACK.toString();
                    }
                    System.out.print(colorBar + "|" + colorString + "   " + colorBar + "|" + CliColor.RESET);
                }
                System.out.print("\t\t");
            }
            System.out.println();
        }
        System.out.print("    ");

        for (int k = 0; k < numPlayer; k++) {
            System.out.print(mockModel.getMockPlayers().get(k).getPlayerID() + ": points");
            for (int i = 0; i < 32 - mockModel.getMockPlayers().get(k).getPlayerID().length() - 8; i++) {
                System.out.print(" ");
            }
        }
        System.out.println("\n");
    }

    @Override
    public void showHelp() {
        System.out.println("Commands: help");
    }

    @Override
    public void showGame() {

    }

    @Override
    public void showRank(List<Rank> classification) {

    }


    @Override
    public void newTurn(String playerID) throws RemoteException {
        clearCLI();
        mockModel.setCurrentPlayer(playerID);
        mockModel.setTurnPhase(TurnPhase.PICKING);
        showAll();
    }

    private void showAll() {
        showBoard();
        showShelves();
        showStatus();
    }

    @Override
    public void outcomeSelectTiles(List<Tile> tiles) throws RemoteException {
        this.mockModel.setTurnPhase(TurnPhase.INSERTING);
        showTile(tiles);
        showStatus();
    }

    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {
        System.out.println("Tile inserted correctly");
    }


    @Override
    public void outcomeException(Exception e) throws RemoteException {
        printError(e.getMessage());
    }

    public void printError(String error) {
        System.out.println(CliColor.BOLDRED + error + CliColor.RESET);
    }

    @Override
    public void printMessage(String message) {
        System.out.println(CliColor.BOLDGREEN + message + CliColor.RESET);
    }

    @Override
    public void outcomeLogin(String localPlayer, String lobbyID) throws RemoteException {
        System.out.println("You logged into the lobby");
        mockModel.setLocalPlayer(localPlayer);
        mockModel.setLobbyID(lobbyID);
        network.startPing(localPlayer, lobbyID);
    }


    @Override
    public void allGame(MockModel mockModel) throws RemoteException {
        this.mockModel = mockModel;
        this.controller = new LightController(mockModel.getLocalPlayer());
        if (mockModel.getChat() != null) fixChat();
        newTurn(mockModel.getCurrentPlayer());
        while (true) {
            try {
                if (!(System.in.available() > 0)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                System.in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.inputThread = new Thread(() -> {
            while (true) {
                String input = this.scanner.nextLine();
                if (input != null && !input.isEmpty()) {
                    controller.elaborate(input);
                }
            }
        });
        this.inputThread.start();
    }

    private void fixChat() {
        mockModel.getChat().removeIf(message -> message.to() != null && !message.to().equals(mockModel.getLocalPlayer()));
    }

    public void clearCLI() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.flush();
    }

}
