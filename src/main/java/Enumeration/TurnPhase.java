package Enumeration;

public enum TurnPhase {
    PICKING("Pick a number from 1 to 3 tile from the board, type \"st -(x,y)\""),
    INSERTING("Insert the tiles in a column in your shelf, type \"-it number of column \"");

    private final String action;

    TurnPhase(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}
