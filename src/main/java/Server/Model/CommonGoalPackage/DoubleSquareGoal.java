package Server.Model.CommonGoalPackage;

import Model.*;
import Server.Model.*;

import java.util.*;

public class DoubleSquareGoal extends CommonGoal {

    public DoubleSquareGoal(int nPlayer) {
        assert nPlayer<=4;
        assert nPlayer>=2;

        this.accomplished = new ArrayList<>();
        this.scoringToken = new Stack<>();

        if(nPlayer==2) {
            scoringToken.push(4);
            scoringToken.push(8);
        } else if(nPlayer==3){
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        } else if(nPlayer==4){
            scoringToken.push(2);
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }
        this.description = "Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2. Le tessere dei due gruppi devono essere dello stesso tipo.";
    }

    @Override
    public void check(Player player) {
        Shelf shelf = player.getShelf();
        HashMap<Coordinates, Color> squareTile = new HashMap<>();
        Coordinates c;
        for (int i=5; i>=1; i--){
            for (int j=0; j<=3; j++){
                if (shelf.getTile(i,j) != null &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i-1,j).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i,j+1).getTileColor() &&
                        shelf.getTile(i,j).getTileColor() == shelf.getTile(i-1,j+1).getTileColor()){
                    c = new Coordinates(i,j);
                    squareTile.put(c,shelf.getTile(i,j).getTileColor());
                }
            }
        }
        for (Map.Entry<Coordinates, Color> entry : squareTile.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Color color = entry.getValue();
            for (Map.Entry<Coordinates, Color> entry2 : squareTile.entrySet()) {
                Coordinates coordinates2 = entry2.getKey();
                Color color2 = entry2.getValue();
                int absX = Math.abs(coordinates.getX() - coordinates2.getX());
                int absY = Math.abs(coordinates.getY() - coordinates2.getY());
                if ( !coordinates.equals(coordinates2) && (absY >= 2 || absX >= 2) && color == color2) {
                    accomplished.add(player.getID());
                    player.updateScore(scoringToken.pop());
                }
            }
        }
    }
}

