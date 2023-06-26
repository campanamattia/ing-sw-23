package Utils.MockObjects;

import java.io.Serializable;
import java.util.Stack;

/**
 * The MockCommonGoal class represents a mock common goal object.
 * It implements the Serializable and Cloneable interfaces.
 */
public class MockCommonGoal implements Serializable, Cloneable{
    /**
     * The scoringToken attribute represents the scoring token available.
     */
    private Stack<Integer> scoringToken;
    /**
     * The enumeration attribute represents the enumeration of the common goal.
     */
    private int enumeration;
    /**
     * The description attribute represents the description of the common goal.
     */
    private String description;

    /**
     * It returns the scoring token stack.
     * @return the scoring token stack
     */
    public Stack<Integer> getScoringToken() {
        return scoringToken;
    }

    /**
     * It sets the scoring token stack.
     * @param scoringToken the scoring token stack
     */
    public void setScoringToken(Stack<Integer> scoringToken) {
        this.scoringToken = scoringToken;
    }

    /**
     * It returns the enumeration of the common goal.
     * @return the enumeration of the common goal
     */
    public int getEnumeration() {
        return enumeration;
    }

    /**
     * It sets the enumeration of the common goal.
     * @param enumeration the enumeration of the common goal
     */
    public void setEnumeration(int enumeration) {
        this.enumeration = enumeration;
    }

    /**
     * It returns the description of the common goal.
     * @return the description of the common goal
     */
    public String getDescription() {
        return description;
    }

    /**
     * It sets the description of the common goal.
     * @param description the description of the common goal
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Creates and returns a deep copy of the mock common goal object.
     *
     * @return A deep copy of the mock common goal object.
     */
    @Override
    public MockCommonGoal clone() {
        try {
            MockCommonGoal mockCommonGoal = (MockCommonGoal) super.clone();

            mockCommonGoal.scoringToken = (Stack<Integer>) scoringToken.clone();
            mockCommonGoal.enumeration = enumeration;
            mockCommonGoal.description = description;

            return mockCommonGoal;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * It checks if the object passed as parameter is equal to the mock common goal.
     * @param obj the object to be compared
     * @return true if the object is equal to the mock common goal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MockCommonGoal mockCommonGoal) {
            return mockCommonGoal.enumeration == enumeration;
        }
        return false;
    }
}
