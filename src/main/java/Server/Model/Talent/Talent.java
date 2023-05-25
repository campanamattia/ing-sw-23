package Server.Model.Talent;

import Interface.Scout;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract class Talent <O>{
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

    public void notifyScouts(O objects){
        for(Scout scout : this.scouts) {
            try {
                scout.update(objects);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Scout> getScouts() {
        return scouts;
    }
}
