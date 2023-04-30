package Client;

import Client.View.*;
import Client.Network.*;
import Client.Controller.Controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

// args[0] = program name, args[1] = type of view

public class ClientApp {
    public void main(String[] args) throws IOException {
        int port;
        View view;
        String input;
        String playerID;
        Network network;
        String ipAddress;
        Controller controller;
        Scanner scanner = new Scanner(System.in);

        // view
        view = ViewFactory.getView(Objects.requireNonNullElse(args[1], "CLI"));

        // network
        input = scanner.nextLine();
        System.out.println("Insert type of connection: ");
        network = NetworkFactory.getNetwork(Objects.requireNonNullElse(input, "SOCKET"));

        //port
        input = scanner.nextLine();
        System.out.println("Insert port number: ");
        port = Integer.parseInt(input);
        network.setPort(port);

        // ipAddress
        input = scanner.nextLine();
        System.out.println("Insert ipAddress ");
        ipAddress = Objects.requireNonNullElse(input, "127.0.0.1");
        network.setIpAddress(ipAddress);

        // playerID
        input = scanner.nextLine();
        System.out.println("Chose a nickname: ");
        while( input == null ){
            System.out.println("Nickname not valid, please chose another one: ");
            input = scanner.nextLine();
        }
        playerID = input;


        // controller
        controller = new Controller(view,network,playerID);
        controller.start();
    }
}