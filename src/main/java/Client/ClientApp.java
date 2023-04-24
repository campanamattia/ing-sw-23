package Client;

import Client.View.*;
import Client.Netwrok.*;
import Client.Controller.Controller;

public class ClientApp {
    public static void main(String[] args) {
        View view;
        Network network;
        Controller controller;
        do{
            view = ViewFactory.getView(args[1]);
            if(view == null)
                System.out.println("Wrong input, select view again");
            //wait a scan to save in args[1]
        }while(view==null);
        controller = new Controller();
        controller.inputReader();
        //we now need to ask for the connection type and initialize the right network object
    }


}
