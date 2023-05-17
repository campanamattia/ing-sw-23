package Utils.MockObjects;

import Enumeration.TurnPhase;
import Utils.ChatMessage;
import Server.Model.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class MockModel {
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


    public void setMockModel(GameModel model) {

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

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }
}
