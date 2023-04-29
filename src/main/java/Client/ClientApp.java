package Client;

import Client.View.*;
import Client.Network.*;
import Client.Controller.Controller;

// args[0] = program name, args[1] = type of view

public class ClientApp {
    public void main(String[] args) {
        View view;
        Network network;
        String ipAddress;
        Controller controller;

        // view
        if(ViewFactory.getView(args[1]) != null) {
            view = ViewFactory.getView(args[1]);
        }else
            view = ViewFactory.getView("CLI");

        // network
        System.out.println("Insert type of connection: ");
        if(System.console().readLine() != null)
            network = NetworkFactory.getNetwork(System.console().readLine());
        else
            network = NetworkFactory.getNetwork("SOCKET");

        // ipAddress
        System.out.println("Insert ipAddress ");
        if(System.console().readLine() != null)
            ipAddress = System.console().readLine();
        else
            ipAddress = "127.0.0.1";

        // controller
        controller = new Controller(view,network,ipAddress);

    }
}