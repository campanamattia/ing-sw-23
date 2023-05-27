package Client;

import Client.View.*;

public class ClientApp {

    public static void main (String[] args) {
        String viewName;

        if (args.length > 0) {
            viewName = args[0];
            if (viewName.equalsIgnoreCase("GUI") || viewName.equalsIgnoreCase("CLI")) {
                ViewFactory.instanceView(viewName);
            }
            else {
                ViewFactory.instanceView("CLI");
            }
        }
    }
}
