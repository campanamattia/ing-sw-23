package Server.Model;

import Exception.CommonGoal.NullPlayerException;

import java.util.*;

/**
 * The CommonGoal class represents a common goal that can be accomplished by players.
 * It contains a list of Id_players, a stack of scoring tokens, an enumeration, and s description.
 */
public abstract class CommonGoal {

    /**
     * The list of ID_player that achieved common goal.
     */
    protected List<String> accomplished;

    /**
     * The stack of scoring tokens associated with common goal.
     */
    protected Stack<Integer> scoringToken;

    /**
     * The enumeration of  common goal.
     */
    protected int enumeration;

    /**
     * The description of common goal.
     */
    protected String description;


    /**
     * Getter for accomplished.
     * @return the list of accomplishments associated with this common goal.
     */
    public List<String> getAccomplished() {
        return accomplished;
    }

    /**
     * Getter for scoringToken.
     * @return the stack of scoring tokens associated with this common goal.
     */
    public Stack<Integer> getScoringToken() {
        return scoringToken;
    }

    /**
     * Getter for enumeration.
     * @return the enumeration of this common goal.
     */
    public int getEnumeration() {
        return enumeration;
    }

    /**
     Getter for description.
     @return the description of this CrossGoal.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the given player has accomplished this common goal.
     * @param player the player to check.
     * @throws NullPlayerException if the given player is null.
     */
    public abstract void check(Player player) throws NullPlayerException;
}
