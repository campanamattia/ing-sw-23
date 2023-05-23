package Server.Model.Talent;

import Interface.Scout;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract class Talent <T extends Scout, O>{
    protected List<T> scouts;

    public Talent(){
        this.scouts = new ArrayList<>();
    }

    public void addScout(T scout){
        this.scouts.add(scout);
    }

    public void removeScout(T scout){
        this.scouts.remove(scout);
    }

    public void notifyScouts(O objects){
        for(T scout : this.scouts) {
            try {
                scout.update(objects);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<T> getScouts() {
        return scouts;
    }
}
