package Client;

import Client.View.*;
import Client.Network.*;
import Client.Controller.Controller;

import java.util.Objects;
import java.util.Scanner;

// args[0] = program name, args[1] = type of view

public class ClientApp {
    public void main(String[] args) {
        int port;
        View view;
        String input;
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

        // ipAddress
        input = scanner.nextLine();
        System.out.println("Insert ipAddress ");
        ipAddress = Objects.requireNonNullElse(input, "127.0.0.1");

        // controller
        controller = new Controller(view,network,ipAddress,port);
        controller.start();

    }
}