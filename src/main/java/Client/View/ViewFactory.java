package Client.View;

import Client.View.Cli.Cli;

public class ViewFactory {
    public static View getView(String view){
        switch (view) {
            case "GUI" -> {
                return new Gui();
            }
            case "CLI" -> {
                return new Cli();
            }
        }
        return null;
    }
}
