package Server.Model;

import Interface.Scout;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static Server.ServerApp.executorService;
import static Server.ServerApp.logger;

public class Talent {
    protected List<Scout> scouts;

    public Talent(){
        this.scouts = new ArrayList<>();
    }

    public void addScout(Scout scout){
        this.scouts.add(scout);
    }

    public void removeScout(Scout scout){
        this.scouts.remove(scout);
    }

    public void onEvent(Object O){
        for(Scout scout : this.scouts) {
            executorService.execute(()-> {
                try {
                    scout.update(O);
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
        }
    }

    public List<Scout> getScouts() {
        return scouts;
    }
}
