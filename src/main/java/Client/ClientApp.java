package Client;

import Client.View.*;

import java.util.Scanner;

public class ClientApp {

    /*
    public static void main(String[] args) {
        String viewName;

        if(!args[0].equals("CLI")){
            viewName = "GUI";
        }else{
            viewName = args[1];
        }
        ViewFactory.instanceView(viewName);
    }
     */

    public static void main (String[] args) {
        String viewName;
        boolean correct = false;

        while (!correct) {
            System.out.println("please insert 'CLI' or 'GUI'");
            Scanner scanner = new Scanner(System.in);
            viewName = scanner.nextLine();
            if (viewName.equalsIgnoreCase("GUI") || viewName.equalsIgnoreCase("CLI")) {
                ViewFactory.instanceView(viewName);
                correct = true;
            }
            scanner.close();
        }
    }
}
