package Utils.MockObjects;

import java.io.Serializable;
import java.util.Stack;

public class MockCommonGoal implements Serializable, Cloneable{
    private Stack<Integer> scoringToken;
    private int enumeration;
    private String description;

    public Stack<Integer> getScoringToken() {
        return scoringToken;
    }

    public void setScoringToken(Stack<Integer> scoringToken) {
        this.scoringToken = scoringToken;
    }

    public int getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(int enumeration) {
        this.enumeration = enumeration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    @SuppressWarnings("unchecked")
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
}
