package Client.View;

import Client.View.Cli.Cli;
import Client.View.Gui.Gui;

import java.rmi.RemoteException;

@SuppressWarnings("")
public class ViewFactory {
    public static View instanceView(String view) throws RemoteException {
        if (view.equalsIgnoreCase("CLI")) {
            return new Cli();
        }
        return new Gui();
    }
}
