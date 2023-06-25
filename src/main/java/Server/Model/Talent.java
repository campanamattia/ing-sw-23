package Server.Model;

import Interface.Scout;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static Server.ServerApp.executorService;
import static Server.ServerApp.logger;

/**
 * The Talent class represents a collection of scouts that can receive updates.
 * It allows adding and removing scouts, and notifying them of events by invoking the update method.
 */
public class Talent {
    /**
     * The list of scouts that are looking to this talent.
     */
    private final List<Scout> scouts;

    /**
     * Constructs a new Talent object with an empty list of scouts.
     */
    public Talent(){
        this.scouts = new ArrayList<>();
    }

    /**
     * Adds a scout to the list of scouts.
     *
     * @param scout the scout to be added
     */
    public void addScout(Scout scout){
        this.scouts.add(scout);
    }

    /**
     * Removes a scout from the list of scouts.
     *
     * @param scout the scout to be removed
     */
    public void removeScout(Scout scout){
        this.scouts.remove(scout);
    }

    /**
     * Notifies all scouts in the list of an event by invoking their update method with the specified object.
     * Each scout is executed in a separate thread using the executor service.
     *
     * @param O the object representing the event
     */
    public void onEvent(Object O){
        for(Scout scout : this.scouts) {
            logger.info("Scout: " + scout.getClass().getSimpleName() + " is updating for " + O.getClass().getSimpleName());
            executorService.execute(()-> {
                try {
                    scout.update(O);
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
        }
    }

    /**
     * Returns the list of scouts.
     *
     * @return the list of scouts
     */
    public List<Scout> getScouts() {
        return scouts;
    }
}
