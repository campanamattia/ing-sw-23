package Server.Model;

public class Player {
    private final String nicknameid;
    private PersonalGoal personalGoal;
    private int score;
    private boolean status;
    
    private Shelf myShelf;

    public Player(String nicknameid, PersonalGoal pGoal) {
        this.nicknameid = nicknameid;
        this.status = true;
        this.score = 0;
        this.personalGoal = pGoal;
        this.myShelf = new Shelf();
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int score){
        this.score += score;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getID() {
        return nicknameid;
    }

    public boolean equals(String s) {
        return this.nicknameid.equals(s);
    }

    public Shelf getMyShelf() {
        return myShelf;
    }

    public void endGame() {
        this.updateScore(myShelf.checkEndGame());
        this.updateScore(personalGoal.check(myShelf.getMyShelf()));
    }

    public Boolean getStatus(){
        return this.status;
    }
}
