package Utils.MockObjects;

import Enumeration.TurnPhase;
import Utils.ChatMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The MockModel class represents a lighter version of the model object for the client use.
 * It implements the Serializable and Cloneable interfaces.
 */
public class MockModel implements Serializable, Cloneable {
    /**
     * The MockBoard object.
     */
    private MockBoard mockBoard;
    /**
     * The list of MockPlayer objects.
     */
    private List<MockPlayer> mockPlayers;
    /**
     * The list of MockCommonGoal objects.
     */
    private List<MockCommonGoal> mockCommonGoal;
    /**
     * The stack of ChatMessage objects.
     */
    private Stack<ChatMessage> chat;
    /**
     * The current player.
     */
    private String currentPlayer;
    /**
     * The current turn phase.
     */
    private TurnPhase turnPhase;

    /**
     * Sets the mock board.
     *
     * @param mockBoard The mock board to be set.
     */
    public void setMockBoard(MockBoard mockBoard) {
        this.mockBoard = mockBoard;
    }

    /**
     * Returns the mock board.
     *
     * @return The mock board.
     */
    public MockBoard getMockBoard() {
        return mockBoard;
    }

    /**
     * Returns the list of mock common goals.
     *
     * @return The list of mock common goals.
     */
    public List<MockCommonGoal> getMockCommonGoal() {
        return mockCommonGoal;
    }

    /**
     * It first finds the mock common goal in the list, and then it updates it.
     *
     * @param mockCommonGoal The mock common goal to be set.
     */
    public void setMockCommonGoal(MockCommonGoal mockCommonGoal) {
        for (MockCommonGoal mock : this.mockCommonGoal) {
            if (mock.equals(mockCommonGoal)) {
                this.mockCommonGoal.set(this.mockCommonGoal.indexOf(mock), mockCommonGoal);
                return;
            }
        }
    }

    /**
     * It first finds the mock player in the list, and then it updates it.
     *
     * @param mockPlayer The mock player to be set.
     */
    public void addMockPlayer(MockPlayer mockPlayer) {
        if (this.mockPlayers == null)
            this.mockPlayers = new ArrayList<>();
        this.mockPlayers.add(mockPlayer);
    }

    /**
     * Returns the list of mock players.
     *
     * @return The list of mock players.
     */
    public List<MockPlayer> getMockPlayers() {
        return mockPlayers;
    }

    /**
     * It returns a specific mock player using the playerID.
     *
     * @param playerID The mock player to be set.
     * @return The mock player.
     */
    public MockPlayer getPlayer (String playerID){
        return mockPlayers.stream()
                .filter(mockPlayer -> mockPlayer.getPlayerID().equals(playerID))
                .findFirst()
                .orElse(null);
    }

    /**
     * It returns the stack of chat messages.
     * @return chat The stack of chat messages.
     */
    public Stack<ChatMessage> getChat() {
        return chat;
    }

    /**
     * It sets the stack of chat messages.
     * @param chat The stack of chat messages.
     */
    public void setChat(Stack<ChatMessage> chat) {
        this.chat = chat;
    }

    /**
     * It adds a chat message to the stack.
     * @param message The chat message to be added.
     */
    public void addMessage(ChatMessage message) {
        chat.add(message);
    }

    /**
     * It returns the current player.
     * @return currentPlayer The current player.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * It sets the current player.
     * @param turnPlayer The current player.
     */
    public void setCurrentPlayer(String turnPlayer) {
        this.currentPlayer = turnPlayer;
    }

    /**
     * It adds a mock common goal to the list.
     * If the list is null, it creates a new one.
     * @param mock The mock common goal to be added.
     */
    public void addMockCommonGoal(MockCommonGoal mock) {
        if (mockCommonGoal == null)
            mockCommonGoal = new ArrayList<>();
        mockCommonGoal.add(mock);
    }

    /**
     * It returns the current turn phase.
     * @return turnPhase The current turn phase.
     */
    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    /**
     * It sets the current turn phase.
     * @param turnPhase The current turn phase.
     */
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    /**
     * Creates and returns a deep copy of the MockModel object.
     *
     * @return A deep copy of the MockModel object.
     */
    @Override
    public MockModel clone() {
        try {
            MockModel mockModel = (MockModel) super.clone();

            mockModel.mockBoard = mockBoard.clone();
            mockModel.mockPlayers = new ArrayList<>();
            for (MockPlayer mockPlayer : mockPlayers) {
                mockModel.mockPlayers.add(mockPlayer.clone());
            }
            mockModel.mockCommonGoal = new ArrayList<>();
            for (MockCommonGoal mockCommonGoal : mockCommonGoal) {
                mockModel.mockCommonGoal.add(mockCommonGoal.clone());
            }
            mockModel.chat = (Stack<ChatMessage>) chat.clone();
            mockModel.currentPlayer = currentPlayer;

            return mockModel;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * It first finds the mock common goal in the list, and then it updates it.
     * @param mockCommonGoal The mock common goal to be updated.
     */
    public void update(MockCommonGoal mockCommonGoal) {
        for(MockCommonGoal mock : this.mockCommonGoal) {
            if(mock.equals(mockCommonGoal)) {
                this.mockCommonGoal.set(this.mockCommonGoal.indexOf(mock), mockCommonGoal);
            }
        }
    }

    /**
     * It first finds the mock player in the list, and then it updates it.
     * @param mockPlayer The mock player to be updated.
     */
    public void update(MockPlayer mockPlayer) {
        for(MockPlayer mock : this.mockPlayers) {
            if(mock.equals(mockPlayer)) {
                this.mockPlayers.set(this.mockPlayers.indexOf(mock), mockPlayer);
            }
        }
    }
}
