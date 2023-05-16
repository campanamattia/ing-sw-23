package Client.View;

import Client.View.Cli.Cli;

public class ViewFactory {
    public static void instanceView(String view) {
        switch (view) {
            case "GUI" -> new Gui();
            case "CLI" -> new Cli();
        }
    }
}
