package Server.Model.Player;

import Exception.Player.ColumnNotValidException;
import Utils.Tile;

import java.util.List;

/**
 * The Player class represents a player in the game.
 * It contains information about the player's personal goal, shelf, score and status.
 */
public class Player {
    /**
     * The player's ID.
     */
    private final String playerID;
    /**
     * The player's personal goal.
     */
    private final PersonalGoal personalGoal;
    /**
     * The player's shelf.
     */
    private final Shelf myShelf;
    /**
     * The player's score.
     */
    private int score;
    /**
     * The player's status.
     */
    private boolean status;

    /**
     * Constructs a Player object based on the provided player ID and personal goal.
     *
     * @param playerID the player's ID
     * @param pGoal    the player's personal goal
     */
    public Player(String playerID, PersonalGoal pGoal) {
        this.playerID = playerID;
        this.status = true;
        this.score = 0;
        this.personalGoal = pGoal;
        this.myShelf = new Shelf();
    }

    /**
     * Inserts the given tiles into the given column of the player's shelf.
     *
     * @param n     the column number
     * @param tiles the tiles to be inserted
     * @throws ColumnNotValidException if the column number is not valid
     */
    public void insert(int n, List<Tile> tiles) throws ColumnNotValidException {
        myShelf.insert(n, tiles);
    }

    /**
     * Updates the player's score by adding the given score.
     *
     * @param score the score to be added
     */
    public void updateScore(int score) {
        this.score = this.score + score;
    }

    /**
     * Sets the player's status to the given status.
     *
     * @param status the status to be set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Ends the game for the player by checking the personal goal and the shelf.
     */
    public void endGame() {
        this.updateScore(personalGoal.check(myShelf.getMyShelf()));
        this.updateScore(myShelf.checkEndGame());
    }

    /**
     * Returns the player's score.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the player's ID.
     *
     * @return the player's ID
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Returns the player's personal goal.
     *
     * @return the player's personal goal
     */
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Returns the player's shelf.
     *
     * @return the player's shelf
     */
    public Shelf getMyShelf() {
        return myShelf;
    }

    /**
     * Returns the player's status.
     *
     * @return the player's status
     */
    public Boolean getStatus() {
        return this.status;
    }

    /**
     * Returns true if the given player ID is equal to the player's ID.
     *
     * @param playerID the player ID to be compared
     * @return true if the given player ID is equal to the player's ID
     */
    public boolean equals(String playerID) {
        return this.playerID.equals(playerID);
    }
}
