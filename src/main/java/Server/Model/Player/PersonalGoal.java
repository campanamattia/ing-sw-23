package Server.Model.Player;

import Enumeration.Color;
import Utils.Coordinates;
import Utils.Tile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PersonalGoal {
    private Tile[][] pGoal;

    public PersonalGoal(JsonObject json) {
        pGoal = new Tile[6][5];
        Coordinates cd;
        for(Color tmp : Color.values()){
            cd = takeCoordinates(json.get(tmp.toString()).getAsJsonArray());
            pGoal[cd.x()][cd.y()] = new Tile(tmp);
        }
    }
    public Coordinates takeCoordinates(JsonArray json) {
        return new Coordinates(json.get(0).getAsInt(), json.get(1).getAsInt());
    }
    public int check(Tile[][] myshelf){
        int count = 0;
        int points = 0;
        for(int i=0; i<6; i++){
            for(int j=0;j<5;j++){
                if(pGoal[i][j] != null && myshelf[i][j] != null){
                    if (myshelf[i][j].getTileColor() == pGoal[i][j].getTileColor()) count++;
                }
            }
        }

        switch(count){
            case 1: points=1;
            case 2: points=2;
            case 3: points=4;
            case 4: points=6;
            case 5: points=9;
            case 6: points=12;
        }

        return points;
    }

    public Tile getPgoalTile(int i, int j){
        return pGoal[i][j];
    }
}
