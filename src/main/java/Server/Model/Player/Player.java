package Server.Model.Player;

import Exception.Player.ColumnNotValidException;
import Utils.Tile;

import java.util.List;

/**
 * The Player class represents a player in the game.
 * It contains information about the player's personal goal, shelf, score and online.
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
    private int sharedScore;

    private int personalScore;

    private int patternScore;
    /**
     * The player's online.
     */
    private boolean online;

    /**
     * Constructs a Player object based on the provided player ID and personal goal.
     *
     * @param playerID the player's ID
     * @param pGoal    the player's personal goal
     */
    public Player(String playerID, PersonalGoal pGoal) {
        this.playerID = playerID;
        this.online = true;
        this.personalScore = 0;
        this.patternScore = 0;
        this.sharedScore = 0;
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
    public void updateSharedScore(int score) {
        this.sharedScore = this.sharedScore + score;
    }

    /**
     * Updates the player's score by adding the given score.
     *
     * @param score the score to be added
     */
    public void updatePersonalScore(int score) {
        this.personalScore = this.personalScore + score;
    }

    /**
     * Updates the player's score by adding the given score.
     *
     * @param score the score to be added
     */
    public void updatePatternScore(int score) {
        this.patternScore = this.patternScore + score;
    }

    /**
     * Sets the player's online to the given online.
     *
     * @param online the online to be set
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Ends the game for the player by checking the personal goal and the shelf.
     */
    public void endGame() {
        updatePersonalScore(personalGoal.check(myShelf.getMyShelf()));
        updatePatternScore(myShelf.checkEndGame());
    }

    /**
     * Returns the player's score.
     *
     * @return the player's score
     */
    public int getTotalScore() {
        return this.sharedScore + this.personalScore + this.patternScore;
    }

    public int getSharedScore() {
        return sharedScore;
    }

    public int getPersonalScore() {
        return personalScore;
    }

    public int getPatternScore() {
        return patternScore;
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
     * Returns the player's online.
     *
     * @return the player's online
     */
    public Boolean isOnline() {
        return this.online;
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
