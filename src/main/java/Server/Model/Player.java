package Server.Model;

import com.google.gson.annotations.Expose;

public class Player {
    @Expose
    private final String nicknameid;
    @Expose
    private PersonalGoal personalGoal;
    @Expose
    private int score;
    @Expose
    private Shelf myShelf;
    private boolean status;

    public Player(String nicknameid, PersonalGoal pGoal) {
        this.nicknameid = nicknameid;
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
        return nicknameid;
    }

    public boolean equals(String s) {
        return this.nicknameid.equals(s);
    }


    public void endGame() {
        this.updateScore(myShelf.checkEndGame());
        this.updateScore(personalGoal.check(myShelf.getMyShelf()));
    }


    public int getScore() {
        return score;
    }

    public String getNicknameid() {
        return nicknameid;
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
