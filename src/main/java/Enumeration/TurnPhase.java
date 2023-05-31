package Enumeration;

public enum TurnPhase {
    PICKING("Pick a number from 1 to 3 tile from the board, type 'st -(x1,y1)(x2,y2)(x3,y3)'"),
    INSERTING("Insert the tiles in a column in your shelf, type 'it -order of tiles x,y,z / number of column'");

    private final String action;

    TurnPhase(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}
