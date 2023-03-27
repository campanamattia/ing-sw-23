package Server.Model.CommonGoalPackage;

import Server.Model.CommonGoal;
import Server.Model.Player;
import Server.Model.Shelf;

import java.util.ArrayList;
import java.util.Stack;

public class SameEightGoal extends CommonGoal {

    public SameEightGoal(int nPlayer) {
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
        this.description = "Otto tessere dello stesso tipo. Non ci sono restrizioni sulla posizione di queste tessere. Cinque colonne di altezza crescente o decrescente: a partire dalla prima colonna a sinistra o a destra, ogni colonna successiva ";
    }

    @Override
    public void check(Player player) {
        Shelf shelf = player.getShelf();
        int countGreen=0, countBlue=0, countAzure=0, countYellow=0, countWhite=0, countPink=0;
        for (int i=5; i>=0; i--) {
            for(int j=4; j>=0; j--){
                if (shelf.getTile(i,j) == null )
                    continue;
                switch (shelf.getTile(i,j).getTileColor()){
                    case PINK -> {
                        countPink++;
                        if (countPink==8){
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case AZURE -> {
                        countAzure++;
                        if (countAzure==8){
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case BLUE -> {
                        countBlue++;
                        if (countBlue==8){
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case GREEN -> {
                        countGreen++;
                        if (countGreen==8){
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case WHITE -> {
                        countWhite++;
                        if (countWhite==8){
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                    case YELLOW -> {
                        countYellow++;
                        if (countYellow==8){
                            accomplished.add(player.getID());
                            player.updateScore(scoringToken.pop());
                        }
                    }
                }
            }
        }
    }
}
