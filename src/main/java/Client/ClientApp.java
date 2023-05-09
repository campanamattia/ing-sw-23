package Client;

import Client.View.*;
import java.util.Objects;

public class ClientApp {
    public static void main(String[] args) {

        String viewName = Objects.requireNonNullElse(args[1], "CLI");
        ViewFactory.instanceView(viewName);

    }

}
