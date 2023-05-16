package Client;

import Client.View.*;
import java.util.Objects;

public class ClientApp {
    public static void main(String[] args) {
        String viewName;

        if(!args[1].equals("CLI")){
            viewName = "GUI";
        }else{
            viewName = args[1];
        }
        ViewFactory.instanceView(viewName);
    }

}
