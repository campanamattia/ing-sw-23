package Client.View;

import Client.View.Cli.Cli;

import java.rmi.RemoteException;

@SuppressWarnings("")
public class ViewFactory {
    public static View instanceView(String view) throws RemoteException {
        // TODO: 26/05/23
        switch (view) {
            case "CLI" -> {
                return new Cli();
            }
            default -> throw new RemoteException("View not found");
        }
    }
}
