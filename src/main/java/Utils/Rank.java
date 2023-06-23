package Utils;

import java.io.Serializable;

/**
 * The Rank class represents a player with an ID and score.
 * It is implemented as a record, providing immutability and default implementations of equals(), hashCode(), and toString().
 */
public record Rank (String ID, int score) implements Serializable {
}