package Server.Model;

public class Player {
    private final String nicknameid;
    public int score;
    private boolean status;
    private PersonalGoal personalGoal;
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

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean equals(String s) {
        return this.nicknameid.equals(s);
    }

    public Shelf getMyShelf() {
        return myShelf;
    }
}
