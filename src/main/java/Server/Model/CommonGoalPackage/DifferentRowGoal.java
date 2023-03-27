package Server.Model.CommonGoalPackage;

import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;

import java.util.ArrayList;
import java.util.Stack;

public class DifferentRowGoal extends CommonGoal {

    public DifferentRowGoal(int nPlayer) { //costruttore da rivedere
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
        this.description = "Due righe formate ciascuna da 5 diversi tipi di tessere.";
    }

    @Override
    public void check(Player player) throws NullPointerException {
        Shelf shelf = player.getShelf();
        int countRow=0;
        for(int i=5; i>=0; i--){
            int countTile=0;
            for (int j=0; j<4; j++){
                if(shelf.getTile(i,j) != null && shelf.getTile(i,j+1) != null &&
                        shelf.getTile(i,j).getTileColor() != shelf.getTile(i,j+1).getTileColor()) {
                    countTile++;
                } else continue;

                if (countTile==4) {
                    countRow++;
                }
                if (countRow==2){
                    accomplished.add(player.getID());
                    player.updateScore(scoringToken.pop());
                }
            }
        }
    }
}
