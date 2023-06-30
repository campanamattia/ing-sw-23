package Server.Model;

import Interface.Scout;
import Utils.ChatMessage;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static Server.ServerApp.executorService;
import static Server.ServerApp.logger;

/**
 * The Talent class represents a collection of scouts that can receive updates.
 * It allows adding and removing scouts and notifying them of events by invoking the update method.
 */
public class Talent {
    /**
     * The list of scouts that are looking to this talent.
     */
    private final HashMap<String, Scout> scouts;

    /**
     * Constructs a new Talent object with an empty list of scouts.
     */
    public Talent(){
        this.scouts = new HashMap<>();
    }

    /**
     * Adds a scout to the list of scouts.
     *
     * @param scout the scout to be added
     */
    public void addScout(String playerID, Scout scout){
        this.scouts.put(playerID, scout);
    }

    /**
     * Removes a scout from the list of scouts.
     *
     * @param playerID the scout to be removed
     */
    public void removeScout(String playerID){
        this.scouts.put(playerID, null);
    }

    /**
     * Notifies all scouts in the list of an event by invoking their update method with the specified object.
     * Each scout is executed in a separate thread using the executor service.
     *
     * @param O the object representing the event
     */
    public void onEvent(Object O){
        for(Scout scout : activeScout()) {
            executorService.execute(()-> {
                try {
                    scout.update(O);
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
        }
    }

    private List<Scout> activeScout(){
        return this.scouts.values().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Notifies all scouts in the list when there is a direct message to them.
     * @param toUpdate the list of scouts to be notified
     * @param chatMessage the message to be sent
     */
    public void onEvent(List<String> toUpdate, ChatMessage chatMessage) {
        for(String scout : toUpdate) {
            if (this.scouts.get(scout) == null) continue;
            executorService.execute(()-> {
                try {
                    this.scouts.get(scout).update(chatMessage);
                } catch (RemoteException e) {
                    logger.severe(e.getMessage());
                }
            });
        }
    }
}
