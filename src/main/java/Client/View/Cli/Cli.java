package Client.View.Cli;

import Client.Controller.Controller;
import Client.View.View;
import Server.Model.ChatMessage;
import Server.Model.CommonGoal;
import Utils.Cell;
import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockModel;
import Utils.MockObjects.MockPlayer;
import Utils.Rank;
import Utils.Tile;

import java.rmi.RemoteException;
import java.util.*;

public class Cli extends View {
    Controller clientController;
    MockModel mockModel;

    public Cli () {
        clientController = new Controller(this);
        clientController.start();
        mockModel = new MockModel();
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
    public void showHelp() {

    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
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
    public void showStatus() {

    }

    @Override
    public void showRank(List<Rank> classification) {

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

    public void showCommonGoal (CommonGoal commonGoal) {

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
                mockModel.getMockPlayers().remove(i);
                mockModel.getMockPlayers().add(mockPlayer);
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

    }

    @Override
    public void askLobbySize() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the numbers of players (insert a number between 2 and 4)");
        String input = scanner.nextLine();
        scanner.close();
        clientController.sendLobbySize(input);
    }

    @Override
    public void outcomeSelectTiles(List<Tile> tiles) throws RemoteException {

    }

    @Override
    public void outcomeInsertTiles(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeWriteChat(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeException(Exception e) throws RemoteException {
        System.out.println(e);
    }

    @Override
    public void outcomeLogin(boolean success) throws RemoteException {

    }

    @Override
    public void outcomeLogout(boolean success) throws RemoteException {

    }

    @Override
    public void askPlayerID() throws RemoteException {

    }
}
