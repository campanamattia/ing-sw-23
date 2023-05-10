package Server.Model.Talent;

import Interface.Scout.Scout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Talent <T extends Scout> {
    protected List<T> scout;

    public Talent(){
        this.scout = new ArrayList<>();
    }

    public void addScout(T scout){
        this.scout.add(scout);
    }

    public void removeScout(T scout){
        this.scout.remove(scout);
    }

    public void notifyScout(Objects objects){
        for(T scout : this.scout)
            scout.update(objects);
    }
}
