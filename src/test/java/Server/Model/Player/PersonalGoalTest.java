package Server.Model.Player;

import Enumeration.Color;
import Utils.Tile;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

class PersonalGoalTest {

    @Test
    void check() throws FileNotFoundException {

        //class personal goal with a defined constructor
        class PersonalGoalNoJson {
            private Tile[][] pGoal;

            public PersonalGoalNoJson() {
                pGoal = new Tile[6][5];
                pGoal[0][0] = new Tile(Color.PINK);
                pGoal[5][2] = new Tile(Color.CYAN);
                pGoal[0][2] = new Tile(Color.BLUE);
                pGoal[3][1] = new Tile(Color.YELLOW);
                pGoal[1][4] = new Tile(Color.GREEN);
                pGoal[2][3] = new Tile(Color.WHITE);
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
                    case 0: points = 0;
                    break;
                    case 1: points=1;
                    break;
                    case 2: points=2;
                    break;
                    case 3: points=4;
                    break;
                    case 4: points=6;
                    break;
                    case 5: points=9;
                    break;
                    case 6: points=12;
                    break;
                }

                return points;
            }

            public Tile getPgoalTile(int i, int j){
                return pGoal[i][j];
            }
        }

        PersonalGoalNoJson pgoal = new PersonalGoalNoJson();
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                if (pgoal.getPgoalTile(i,j) != null) {
                    System.out.println(pgoal.getPgoalTile(i, j).getTileColor() + " ");
                }
            }

        }
        Tile[][] GoalShelf;
        GoalShelf = new Tile[6][5];

        //1 tile check
        GoalShelf[0][0] = new Tile(Color.PINK);
        int p1 = pgoal.check(GoalShelf);
        System.out.println(p1);

        //check's for test
        int count = 0;
        for(int i=0; i<6; i++){
            for(int j=0;j<5;j++){
                if((pgoal.getPgoalTile(i,j) != null) && (GoalShelf[i][j] != null)){
                    if (GoalShelf[i][j].getTileColor() == pgoal.getPgoalTile(i,j).getTileColor()) count++;
                }
            }
        }

        System.out.println(count);

        //2 tiles check
        GoalShelf[5][2] = new Tile(Color.CYAN);
        int p2 = pgoal.check(GoalShelf);
        System.out.println(p2);

        //3 tiles check
        GoalShelf[0][2] = new Tile(Color.BLUE);
        int p3 = pgoal.check(GoalShelf);
        System.out.println(p3);

        //all tiles check
        GoalShelf[3][1] = new Tile(Color.YELLOW);
        GoalShelf[1][4] = new Tile(Color.GREEN);
        GoalShelf[2][3] = new Tile(Color.WHITE);
        int p4 = pgoal.check(GoalShelf);
        System.out.println(p4);








    }


}