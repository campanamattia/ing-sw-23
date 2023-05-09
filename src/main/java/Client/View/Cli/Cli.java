package Client.View.Cli;

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
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.util.*;

public class Cli extends View {
    ClientController clientController;
    MockModel mockModel = new MockModel();


    public Cli (ClientController clientController) {
        this.clientController = clientController;

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
    public void showMessage() {

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
    public void showWinner(List<Rank> rank) {

    }

    public static void clearCLI() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.flush();
    }

    public void showShelves(@NotNull HashMap<String, Tile[][]> shelves){
        int numColumn = 5;
        int numRow = 6;
        int numPlayer = shelves.size();
        List <Tile[][]> shelfList = shelves.values().stream().toList();
        List <String> names = shelves.keySet().stream().toList();

        for (int i = 0; i < numRow ; i++) {
            System.out.print(" \t");
            for (int k = 0; k < numPlayer; k++) {
                for (int j = 0; j < numColumn; j++) {
                    Tile[][] shelf = shelfList.get(k);
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
            System.out.print(names.get(k) + ": points");
            for (int i = 0; i < 32 - names.get(k).length() - 8; i++) {
                System.out.print(" ");
            }
        }
        System.out.println("\n");
    }

    public void showCommonGoal (CommonGoal commonGoal) {

    }

    @Override
    public void updateBoard(MockBoard mockBoard) throws RemoteException {

    }

    @Override
    public void updateCommonGoal(List<MockCommonGoal> mockCommonGoals) throws RemoteException {

    }

    @Override
    public void updatePlayer(MockPlayer mockPlayer) throws RemoteException {

    }

    @Override
    public void updateChat(Stack<ChatMessage> chatFlow) throws RemoteException {

    }

    @Override
    public void updateLobby(String playerLogged) throws RemoteException {

    }

    @Override
    public void newTurn(String playerID) throws RemoteException {

    }

    @Override
    public void askLobbySize() throws RemoteException {

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
