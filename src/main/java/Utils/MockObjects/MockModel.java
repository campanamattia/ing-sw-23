package Utils.MockObjects;

import Enumeration.TurnPhase;
import Utils.ChatMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MockModel implements Serializable, Cloneable {
    private String lobbyID;
    private String localPlayer;
    private MockBoard mockBoard;
    private List<MockPlayer> mockPlayers;
    private List<MockCommonGoal> mockCommonGoal;
    private Stack<ChatMessage> chat;
    private String currentPlayer;
    private TurnPhase turnPhase;

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    public String getLobbyID() {
        return lobbyID;
    }

    public void setMockBoard(MockBoard mockBoard) {
        this.mockBoard = mockBoard;
    }

    public MockBoard getMockBoard() {
        return mockBoard;
    }

    public void setMockCommonGoal(List<MockCommonGoal> mockCommonGoal) {
        this.mockCommonGoal = mockCommonGoal;
    }

    public List<MockCommonGoal> getMockCommonGoal() {
        return mockCommonGoal;
    }

    public void setMockPlayers(List<MockPlayer> mockPlayers) {
        this.mockPlayers = mockPlayers;
    }

    public void setMockCommonGoal(MockCommonGoal mockCommonGoal) {
        for (MockCommonGoal mock : this.mockCommonGoal) {
            if (mock.equals(mockCommonGoal)) {
                this.mockCommonGoal.set(this.mockCommonGoal.indexOf(mock), mockCommonGoal);
            }
        }
    }

    public void addMockPlayer(MockPlayer mockPlayer) {
        if (this.mockPlayers == null)
            this.mockPlayers = new ArrayList<>();
        this.mockPlayers.add(mockPlayer);
    }

    public List<MockPlayer> getMockPlayers() {
        return mockPlayers;
    }

    public String getLocalPlayer() {
        return localPlayer;
    }

    public void setLocalPlayer(String playerID) {
        this.localPlayer = playerID;
    }

    public Stack<ChatMessage> getChat() {
        return chat;
    }

    public void setChat(Stack<ChatMessage> chat) {
        this.chat = chat;
    }

    public void addMessage(ChatMessage message) {
        chat.add(message);
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String turnPlayer) {
        this.currentPlayer = turnPlayer;
    }

    public void addMockCommonGoal(MockCommonGoal mock) {
        if (mockCommonGoal == null)
            mockCommonGoal = new ArrayList<>();
        mockCommonGoal.add(mock);
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MockModel clone() {
        try {
            MockModel mockModel = (MockModel) super.clone();

            mockModel.lobbyID = lobbyID;
            mockModel.localPlayer = localPlayer;
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
}
