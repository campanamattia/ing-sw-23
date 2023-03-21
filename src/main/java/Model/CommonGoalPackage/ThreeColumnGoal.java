package Model.CommonGoalPackage;

import Model.Color;
import Model.Player;
import Model.CommonGoal;
import Model.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ThreeColumnGoal extends CommonGoal {

    public ThreeColumnGoal(int nPlayer) {
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
        this.description = "Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti. Colonne diverse possono avere combinazioni diverse di tipi di tessere.";
    }

    @Override
    public void check(Player player) {
        Shelf shelf = player.getShelf();
        int countColumn=0;
        for (int j=0; j<5; j++) {
            List<Color> colorRow = new ArrayList<>();
            for (int i=5; i>=0; i--) {
                if (shelf.getTile(i,j) != null) {
                    colorRow.add(shelf.getTile(i, j).getTileColor());
                } else
                    break;
            }
            if (colorRow.stream().distinct().count()<=3 && colorRow.size()==6){
                countColumn++;
            }
            if (countColumn==4){
                accomplished.add(player.getID());
                player.updateScore(scoringToken.pop());
            }
        }
    }
}
