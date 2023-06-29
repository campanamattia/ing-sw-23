package Enumeration;

/**
 * An enumeration representing different phases of a player's turn in a game.
 */
public enum TurnPhase {
    PICKING("Pick a number from 1 to 3 tile from the board, type 'st-(x1,y1)(x2,y2)(x3,y3)'"),
    INSERTING("Insert the tiles in a column in your shelf, type 'it-x,y,z/[A-E]'"),
    WAITING("Waiting for other players to rejoin the game"),
    ENDED("The game is ended, it was a pleasure to play with you!");

    private final String action;

    /**
     * Constructs a turn phase with the specified action description.
     *
     * @param action the action description for the turn phase.
     */
    TurnPhase(String action) {
        this.action = action;
    }

    /**
     * Returns the action description for the turn phase.
     *
     * @return the action description for the turn phase.
     */
    @Override
    public String toString() {
        return action;
    }
}
