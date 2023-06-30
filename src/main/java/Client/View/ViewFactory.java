package Client.View;

import Client.View.Cli.Cli;
import Client.View.Gui.Gui;

import java.rmi.RemoteException;

/**
 * Factory class for creating instances of different views.
 */
@SuppressWarnings("")
public class ViewFactory {
    /**
     * Creates and returns an instance of the specified view type.
     *
     * @param view The type of the view to be instantiated.
     * @return An instance of the specified view type.
     * @throws RemoteException If an exception occurs during remote communication.
     */
    public static View instanceView(String view) throws RemoteException {
        if (view.equalsIgnoreCase("CLI")) {
            return new Cli();
        }
        return new Gui();
    }
}
