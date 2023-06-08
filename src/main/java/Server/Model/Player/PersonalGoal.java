package Server.Model.Player;

import Enumeration.Color;
import Utils.Coordinates;
import Utils.Tile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PersonalGoal {
    private final Tile[][] personalGoal;

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

    public Tile getPgoalTile(int i, int j){
        return personalGoal[i][j];
    }

    public Tile[][] getPersonalGoal() {
        return personalGoal;
    }
}
