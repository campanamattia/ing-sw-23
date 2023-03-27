package Server.Model.CommonGoalPackage;

import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;

import java.util.ArrayList;
import java.util.Stack;

public class StaircaseGoal extends CommonGoal {

    public StaircaseGoal(int nPlayer) {
        assert nPlayer <= 4;
        assert nPlayer >= 2;

        this.accomplished = new ArrayList<>();
        this.scoringToken = new Stack<>();

        if (nPlayer == 2) {
            scoringToken.push(4);
            scoringToken.push(8);
        } else if (nPlayer == 3) {
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        } else if (nPlayer == 4) {
            scoringToken.push(2);
            scoringToken.push(4);
            scoringToken.push(6);
            scoringToken.push(8);
        }
        this.description = "Cinque colonne di altezza crescente o decrescente: a partire dalla prima colonna a sinistra o a destra, ogni colonna successiva deve essere formata da una tessera in più. Le tessere possono essere di qualsiasi tipo";
    }

    @Override
    public void check(Player player) { //devo considerare il caso limite o anche una figura che la contenga?
        Shelf shelf = player.getShelf();
        int countSx = 0, countDx =0;
        for (int i = 5; i >= 1; i--) {
            for (int j = 0; j < 5 && j <= i; j++) {
                if (shelf.getTile(i,j) != null) {
                    countSx++;
                }
            }
        }
        if ((countSx == 15 && shelf.getTile(0,0) == null && shelf.getTile(1,1) == null && shelf.getTile(2,2) == null &&
                shelf.getTile(3,3) == null && shelf.getTile(4,4) == null) ||
                (countSx == 20 && shelf.getTile(0,1) == null && shelf.getTile(1,2) == null && shelf.getTile(2,3) == null &&
                        shelf.getTile(3,4) == null)) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }

        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 5 && j >= i; j++) {
                if (shelf.getTile(i,j) != null) {
                    countDx++;
                }
            }
        }
        if ((countDx == 15 && shelf.getTile(4,0) == null && shelf.getTile(3,1) == null && shelf.getTile(2,2) == null &&
                shelf.getTile(1,3) == null && shelf.getTile(0,4) == null) ||
                (countDx == 20 && shelf.getTile(3,0) == null && shelf.getTile(2,1) == null && shelf.getTile(1,2) == null &&
                        shelf.getTile(0,3) == null)) {
            accomplished.add(player.getID());
            player.updateScore(scoringToken.pop());
        }
    }
}
