package Client.View;

import Client.View.Cli.Cli;

import java.rmi.RemoteException;

@SuppressWarnings("")
public class ViewFactory {
    public static void instanceView(String view) {
        // TODO: 26/05/23
        //case "GUI" -> new Gui();
        if (view.equals("CLI")) {
            try {
                new Cli();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
