package Client.Controller;

import Client.View.View;
import Client.View.ViewFactory;

public class Controller {
    private View view;

    public Controller(View view) {
        this.view = view;
    }

    public void changeView(String view){
        setView(ViewFactory.getView(view));
    }

    private void setView(View view) {
        this.view = view;
    }
}
