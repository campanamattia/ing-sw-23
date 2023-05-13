package Server.Model.Player;

import Server.Model.Talent.PlayerTalent;
import Utils.MockObjects.MockFactory;
import com.google.gson.annotations.Expose;

import java.util.List;

public class Player {
    @Expose
    private final String playerID;
    @Expose
    private PersonalGoal personalGoal;
    @Expose
    private int score;
    @Expose
    private Shelf myShelf;
    private final PlayerTalent talent;
    private boolean status;

    public Player(String playerID, PersonalGoal pGoal) {
        this.playerID = playerID;
        this.status = true;
        this.score = 0;
        this.personalGoal = pGoal;
        this.myShelf = new Shelf();
        this.talent = new PlayerTalent();
    }

    public void updateScore(int score){
        this.score += score;
        this.talent.notifyScouts(MockFactory.getMock(this));
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public PlayerTalent getTalent() {
        return talent;
    }
}
