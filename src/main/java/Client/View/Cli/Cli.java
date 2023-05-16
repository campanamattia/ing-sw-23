package Client.View.Cli;

import Client.Controller.Controller;
import Client.Network.ClientRMI;
import Client.Network.ClientSocket;
import Client.Network.Network;
import Client.View.View;
import Utils.ChatMessage;
import Utils.Cell;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;
import Utils.Rank;
import Utils.Tile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class Cli extends View {
    Controller clientController;
    MockModel mockModel;
    Network network;


    public Cli () {
        clientController = new Controller(this);
        mockModel = new MockModel();
    }

    public void start() throws IOException {
        int port;
        String address;
        showTitle();
        network = askConnection();
        address = askServerAddress();
        port = askServerPort();
        network.init(address,port);
    }


    @Override
    public void showBoard() {
        Cell[][] board = mockModel.getMockBoard().getBoard();
        System.out.println(" \t   0   " + "   1   " + "   2   " + "   3   " + "   4   " + "   5   " + "   6   " + "   7   " + "   8   ");
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getStatus()) {
                    String colorString = board[i][j].getTile().getColor().toString();
                    System.out.print(CliColor.BBLACK + "|" + colorString + i + " , " + j + CliColor.BBLACK + "|" + CliColor.RESET);
                } else {
                    System.out.print(CliColor.BBLACK + "|     |" + CliColor.RESET); //print empty black space
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void showChat() {
        Stack<ChatMessage> chat = mockModel.getChat();
        for (int i=0; i<chat.size(); i++) {
            System.out.println(chat.pop().sender() + ": " + chat.pop().content());
        }
    }

    @Override
    public void showStatus() {
        if (mockModel.getCurrentPlayer().equals(mockModel.getLocalPlayerID())) {
            System.out.print("It's your turn. ");
            System.out.println(mockModel.getTurnPhase());
        }
        else {
            System.out.println("It's NOT your turn. Wait for other players");
        }
    }


    @Override
    public void showTitle() {
        System.out.print("" + CliColor.CLEAR_ALL + CliColor.BOLDYELLOW);
        System.out.println("""
                            ✹ ｡  .  ･ . ∴ * ███╗   ███╗██╗   ██╗    ██████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗. 　･ ∴　　｡ 　
                           ｡    ✦    *      ████╗ ████║╚██╗ ██╔╝   ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝ ∴⋆  ˚  *   .
                             ∴   *  ｡ .  ✹  ██╔████╔██║ ╚████╔╝    ╚█████╗ ███████║█████╗  ██║     █████╗  ██║█████╗   ｡ ·　 ✦   *
                            .   ･  *   ｡  ∴ ██║╚██╔╝██║  ╚██╔╝      ╚═══██╗██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝　   ✹  ｡   ·  ✧
                             ･  .   ✦     * ██║ ╚═╝ ██║   ██║      ██████╔╝██║  ██║███████╗███████╗██║     ██║███████╗ ✦ ∴ 　･ ｡· ∴
                             ✹   ｡ ∴.  ･   .╚═╝     ╚═╝   ╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝ ･　 *　　✹　 ˚""" + CliColor.RESET);

        System.out.println("Complete rules are available here: " + CliColor.BOLDPINK + "https://www.craniocreations.it/prodotto/my-shelfie\n" + CliColor.RESET);
    }

    @Override
    public void showTile(List<Tile> tiles) {
        System.out.print("\t");
        for (int i=0; i < tiles.size(); i++) {
            System.out.print(tiles.get(i).getColor().toString() + "|" + (i+1) + "|");
            System.out.print(CliColor.RESET + "   ");
        }
    }

    @Override
    public void endGame(List<Rank> classification) {

    }

    public static void clearCLI() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.flush();
    }

    public void showShelves(){
        int numColumn = 5;
        int numRow = 6;
        int numPlayer = mockModel.getLobby().size();

        for (int i = 0; i < numRow ; i++) {
            System.out.print(" \t");
            for (int k = 0; k < numPlayer; k++) {
                for (int j = 0; j < numColumn; j++) {
                    Tile[][] shelf = mockModel.getMockPlayers().get(k).getShelf();
                    if (shelf[i][j] != null) {
                        String colorString = shelf[i][j].getColor().toString();
                        System.out.print(CliColor.BBLACK + "|" + colorString + i + "," + j + CliColor.BBLACK + "|" + CliColor.RESET);
                    } else {
                        System.out.print(CliColor.BBLACK + "|   |" + CliColor.RESET);
                    }
                }
                System.out.print("\t\t");
            }
            System.out.println();
        }
        System.out.print("    ");

        for (int k =0; k < numPlayer; k++){
            System.out.print(mockModel.getMockPlayers().get(k).getPlayerID() + ": points");
            for (int i = 0; i < 32 - mockModel.getMockPlayers().get(k).getPlayerID().length() - 8; i++) {
                System.out.print(" ");
            }
        }
        System.out.println("\n");
    }


    @Override
    public void updateBoard(MockBoard mockBoard) throws RemoteException {
        mockModel.setMockBoard(mockBoard);
    }

    @Override
    public void updateCommonGoal(List<MockCommonGoal> mockCommonGoals) throws RemoteException {
        mockModel.setMockCommonGoal(mockCommonGoals);
    }

    @Override
    public void updatePlayer(MockPlayer mockPlayer) throws RemoteException {
        for (int i=0; i<mockModel.getMockPlayers().size(); i++) {
            if (mockModel.getMockPlayers().get(i).getPlayerID().equals(mockPlayer.getPlayerID())) {
                mockModel.getMockPlayers().set(i,mockPlayer);
            }
        }
    }

    @Override
    public void updateChat(Stack<ChatMessage> chat) throws RemoteException {
        mockModel.setChat(chat);
    }

    @Override
    public void updateLobby(String playerLogged) throws RemoteException {
        mockModel.getLobby().add(playerLogged);
    }

    @Override
    public void newTurn(String playerID) throws RemoteException {
        clearCLI();
        mockModel.setCurrentPlayer(playerID);
        showBoard();
        showShelves();
        showHelp();
    }


    @Override
    public void outcomeSelectTiles(List<Tile> tiles) throws RemoteException {
        System.out.print("\t");
        for (int i=0; i < tiles.size(); i++) {
            System.out.print(tiles.get(i).getColor().toString() + "|" + (i+1) + "|");
            System.out.print(CliColor.RESET + "   ");
        }
    }

    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeException(Exception e) throws RemoteException {
        System.out.println(CliColor.RED + e.toString());
    }

    @Override
    public void outcomeLogin(String playerId) throws RemoteException {
        System.out.println("You login into the server");
        mockModel.setLocalPlayerID(playerId);
    }


    public Network askConnection() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select connection Mode (insert SOCKET or RMI): ");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("SOCKET") && !input.equalsIgnoreCase("RMI")) {
            System.out.println(CliColor.RED + "ERROR: you type something wrong, please enter SOCKET or RMI" + CliColor.RESET);
            input = scanner.nextLine();
        }

        System.out.println("Well done you create a" + input.toLowerCase() + "connection");

        if (input.equalsIgnoreCase("SOCKET")){
            return new ClientSocket(this);
        }
        if (input.equalsIgnoreCase("RMI")){
            return new ClientRMI(this);
        }
        return null;
    }

    @Override
    public void askPlayerInfo(List<Collection<String>> lobbyInfo ) throws RemoteException {
        for (Collection<String> lobbyId : lobbyInfo ) {
            for (String gameId : lobbyId) {
                System.out.println(gameId);
            }
        }
    }

    @Override
    public void askLobbySize() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please insert the numbers of players (insert a number between 2 and 4)" );
        String input = scanner.nextLine();

        int playerNumber = Integer.parseInt(input);
        while (playerNumber != 2 && playerNumber != 3 && playerNumber != 4) {
            System.out.println(CliColor.RED + "ERROR: you type something wrong, match can only start with 2, 3 or 4 players" + CliColor.RESET);
            input = scanner.nextLine();
            playerNumber = Integer.parseInt(input);
        }

        System.out.println("You are going to create a new Game, wait for the others players");

        scanner.close();

        //metodo da chiamare in network

    }

    public String askServerAddress() {
        final String DEFAULT_ADDRESS = "127.0.0.1";
        String ip = DEFAULT_ADDRESS;
        boolean validInput = false;
        boolean firstTry = true;

        Scanner scanner = new Scanner(System.in);

        String address;
        do {
            if (!firstTry)
                System.out.println(CliColor.RED + "ERROR: Invalid address! (remember the syntax xxx.xxx.xxx.xxx)" + CliColor.RESET + " Try again.");
            else
                System.out.println("Please enter the server address");

            System.out.println("Insert 'd' for the default value (" + DEFAULT_ADDRESS + "): ");
            address = scanner.nextLine();

            if (address.equalsIgnoreCase("d") || address.equalsIgnoreCase("localhost") || address.equals(DEFAULT_ADDRESS)) {
                validInput = true;
            } else if (clientController.validateIP(address)) {
                ip = address;
                validInput = true;
            } else {
                firstTry = false;
            }
        } while (!validInput);

        scanner.close();

        return address;

    }

    public int askServerPort() {
        final int DEFAULT_PORT = 2807;
        final int MIN_PORT = 1024;
        final int MAX_PORT = 65535;
        int port = DEFAULT_PORT;
        boolean validInput = false;
        boolean firstTry = true;
        boolean notAnInt = false;
        boolean wrongPort = false;

        Scanner scanner = new Scanner(System.in);

        String input = null;
        while (!validInput) {
            if (notAnInt) {
                notAnInt = false;
                System.out.println(CliColor.RED + "ERROR: Please insert only numbers or \"d\"." + CliColor.RESET + " Try again.");
            }
            if (wrongPort) {
                wrongPort = false;
                System.out.println(CliColor.RED + "ERROR: MIN PORT = " + MIN_PORT + ", MAX PORT = " + MAX_PORT + "." + CliColor.RESET + " Try again.");
            }

            System.out.println("Select a valid port between [" + MIN_PORT + ", " + MAX_PORT + "]");
            System.out.println("Insert 'd' for the default value (" + DEFAULT_PORT + "): ");

            input = scanner.nextLine();

            if (input.equalsIgnoreCase("d")) {
                validInput = true;
            } else {
                try {
                    port = Integer.parseInt(input);
                    if (MIN_PORT <= port && port <= MAX_PORT) {
                        validInput = true;
                    } else {
                        wrongPort = true;
                    }
                } catch (NumberFormatException e) {
                    notAnInt = true;
                }
            }
        }

        scanner.close();
        return port;
    }

}
