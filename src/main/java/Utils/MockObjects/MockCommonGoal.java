package Utils.MockObjects;

import java.util.Stack;

public class MockCommonGoal {
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
}
