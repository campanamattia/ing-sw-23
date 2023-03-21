package Model;

public class PersonalGoal {
    private Tile[][] pGoal = new Tile[6][5];

    public PersonalGoal(Tile[][] pGoal){
        this.pGoal = pGoal;
    }
    @Override
    public String toString() {
        return super.toString();
    }
     //TODO: 21/03/23
    public int check(){
        for(int i=0; i<6; i++){
            for(int j=0;j<5;j++){
                if(pGoal[i][j] != null){
                   Shelf myshelfie = getShelf();
                    if(pGoal[i][j] != myShelfie[i][j]) return 0;
                }
            }
        }
        return 1; //valutare il punteggio
    }
}
