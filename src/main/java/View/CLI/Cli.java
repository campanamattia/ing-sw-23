package View.CLI;

import Interface.VirtualView;
import Messages.ServerMessage;

import java.util.Scanner;

public class Cli implements VirtualView {
    private final Scanner scanner = new Scanner(System.in);
    private Client client;

    public static void main(String[] args) {
        new Cli().start();
    }

    private void start() {
        System.out.print("" + CliColor.CLEAR_ALL + CliColor.BOLDYELLOW);
        System.out.println(" ✹ ｡  .  ･ . ∴ * ███╗░░░███╗██╗░░░██╗   ░██████╗██╗░░██╗███████╗███████╗██╗░░░░░██╗███████╗. 　･ ∴　　｡ 　\n" +
                           " ｡    ✦    *     ████╗░████║╚██╗░██╔╝   ██╔════╝██║░░██║██╔════╝██╔════╝██║░░░░░██║██╔════╝ ∴⋆  ˚  *   .\n" +
                           "  ∴   *  ｡ .  ✹  ██╔████╔██║░╚████╔╝░   ╚█████╗░███████║█████╗░░█████╗░░██║░░░░░██║█████╗░░ ｡ ·　 ✦   *  \n" +
                           " .   ･  *   ｡  ∴ ██║╚██╔╝██║░░╚██╔╝░░   ░╚═══██╗██╔══██║██╔══╝░░██╔══╝░░██║░░░░░██║██╔══╝░░　 ✹  ｡   ·  ✧\n" +
                           "  ･  .   ✦     * ██║░╚═╝░██║░░░██║░░░   ██████╔╝██║░░██║███████╗██║░░░░░███████╗██║███████╗ ✦ ∴ 　･ ｡· ∴ \n" +
                           "  ✹   ｡ ∴.  ･   .╚═╝░░░░░╚═╝░░░╚═╝░░░   ╚═════╝░╚═╝░░╚═╝╚══════╝╚═╝░░░░░╚══════╝╚═╝╚══════╝ ･　 *　　✹　 ˚\n" + CliColor.RESET);

        System.out.println("Complete rules are available here: " + CliColor.BOLDPINK + "https://www.craniocreations.it/prodotto/my-shelfie\n" + CliColor.RESET);

        /*boolean socketError = true;
        while (socketError){
            try {
                client = askServerInfo();
                client.init();
                socketError = false;
            } catch (IOException ignored){}
        }
         */
    }

    public void askServerInfo(){

    }

    public void askNickname(){

    }

    public void askNumberPlayer(){}

    public void askAction(PlayersHandler playerActions){}

    public void printCommonGoal(){}

    public void printBoard(){}

    public void printShelf(){}

    public void displayEndgameResult(String winner, String condition){}

    public void displayDisconnectionMessage(String disconnectedNickname, String message){}

    public void displayErrorAndExit(String message){}

    public void displayMessage(String message){}

    public void newTurn(ServerMessage message){}

    public void EndTurn(ServerMessage message){}

    /**
     * Clears the terminal's window.
     */
    public void clearCLI() {
        System.out.print(CliColor.CLEAR_ALL);
        System.out.flush();
    }
}
