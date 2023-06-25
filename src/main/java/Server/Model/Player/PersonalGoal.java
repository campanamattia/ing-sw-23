package Server.Model.Player;

import Enumeration.Color;
import Utils.Coordinates;
import Utils.Tile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The PersonalGoal class represents the personal goal of a player in the game.
 * It contains information about the specific color tiles that the player needs
 * to place on their shelf to achieve a higher score.
 */
public class PersonalGoal {
    /**
     * The 2D array representing the personal goal.
     */
    private final Tile[][] personalGoal;

    /**
     * Constructs a PersonalGoal object based on the provided JSON object.
     *
     * @param json the JSON object containing personal goal information
     */
    public PersonalGoal(JsonObject json) {
        personalGoal = new Tile[6][5];
        Coordinates cd;
        for(Color tmp : Color.values()){
            cd = takeCoordinates(json.get(tmp.toString()).getAsJsonArray());
            personalGoal[cd.x()][cd.y()] = new Tile(tmp);
        }
    }

    private Coordinates takeCoordinates(JsonArray json) {
        return new Coordinates(json.get(0).getAsInt(), json.get(1).getAsInt());
    }

    /**
     * Checks the personal goal against the given player's shelf and returns the score.
     *
     * @param myshelf the player's shelf
     * @return the score based on the personal goal
     */
    public int check(Tile[][] myshelf){
        int count = 0;
        for(int i=0; i<6; i++){
            for(int j=0;j<5;j++){
                if(personalGoal[i][j] != null && myshelf[i][j] != null){
                    if (myshelf[i][j].color() == personalGoal[i][j].color()) count++;
                }
            }
        }

        return switch (count) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 6;
            case 5 -> 9;
            case 6 -> 12;
            default -> 0;
        };
    }

    /**
     * Gets the tile at the specified coordinates in the personal goal.
     *
     * @param i the row index
     * @param j the column index
     * @return the tile at the specified coordinates
     */
    public Tile getPgoalTile(int i, int j){
        return personalGoal[i][j];
    }

    /**
     * Returns the 2D array representing the personal goal.
     *
     * @return the personal goal as a 2D array of tiles
     */
    public Tile[][] getPersonalGoal() {
        return personalGoal;
    }
}
