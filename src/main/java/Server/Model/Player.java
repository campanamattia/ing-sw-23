package Server.Model;

import com.google.gson.annotations.Expose;

public class Player {
    @Expose
    private final String playerID;
    @Expose
    private PersonalGoal personalGoal;
    @Expose
    private int score;
    @Expose
    private Shelf myShelf;
    private boolean status;

    public Player(String playerID, PersonalGoal pGoal) {
        this.playerID = playerID;
        this.status = true;
        this.score = 0;
        this.personalGoal = pGoal;
        this.myShelf = new Shelf();
    }

    public void updateScore(int score){
        this.score += score;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getID() {
        return playerID;
    }

    public boolean equals(String s) {
        return this.playerID.equals(s);
    }


    public void endGame() {
        this.updateScore(myShelf.checkEndGame());
        this.updateScore(personalGoal.check(myShelf.getMyShelf()));
    }


    public int getScore() {
        return score;
    }

    public String getPlayerID() {
        return playerID;
    }

    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    public Shelf getMyShelf() {
        return myShelf;
    }

    public Boolean getStatus(){
        return this.status;
    }
}
