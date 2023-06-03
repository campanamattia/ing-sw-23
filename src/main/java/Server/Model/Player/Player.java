package Server.Model.Player;

import Exception.Player.ColumnNotValidException;
import Utils.Tile;

import java.util.List;

public class Player {
    private final String playerID;
    private final PersonalGoal personalGoal;
    private final Shelf myShelf;

    private int score;
    private boolean status;

    public Player(String playerID, PersonalGoal pGoal) {
        this.playerID = playerID;
        this.status = true;
        this.score = 0;
        this.personalGoal = pGoal;
        this.myShelf = new Shelf();
    }

    public void insert(int n, List<Tile> tiles) throws ColumnNotValidException {
        myShelf.insert(n, tiles);
    }

    public void updateScore(int score) {
        this.score += score;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void endGame() {
        this.updateScore(personalGoal.check(myShelf.getMyShelf()));
        this.updateScore(myShelf.checkEndGame());
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

    public Boolean getStatus() {
        return this.status;
    }

    public boolean equals(String playerID) {
        return this.playerID.equals(playerID);
    }
}
