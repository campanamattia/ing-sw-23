package Server.Model.CommonGoalPackage;

import Server.Model.Color;
import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FourRowGoal extends CommonGoal {

    public FourRowGoal(int nPlayer) {
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
        this.description = "Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti. Righe diverse possono avere combinazioni diverse di tipi di tessere.";
    }

    @Override
    public void check(Player player) {
        Shelf shelf = player.getShelf();
        int countRow=0;
        for (int i = 5; i >= 0; i--) {
            List<Color> colorRow = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                if (shelf.getTile(i,j) != null) {
                    colorRow.add(shelf.getTile(i, j).getTileColor());
                } else
                    break;
            }
            if (colorRow.stream().distinct().count()<=3 && colorRow.size()==5){
                countRow++;
            }
            if (countRow==4){
                accomplished.add(player.getID());
                player.updateScore(scoringToken.pop());
            }
        }
    }
}
