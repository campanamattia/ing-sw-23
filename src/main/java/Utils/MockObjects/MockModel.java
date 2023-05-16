package Utils.MockObjects;

import Enumeration.TurnPhase;
import Utils.ChatMessage;
import Server.Model.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class MockModel {
    private UUID uuid;
    private String localPlayerID;
    private MockBoard mockBoard;
    private List<MockPlayer> mockPlayers;
    private List<MockCommonGoal> mockCommonGoal;
    private Stack<ChatMessage> chat;
    private List<String> lobby;
    private String currentPlayer;
    private TurnPhase turnPhase;

    public MockModel() {
        this.uuid = null;
        this.localPlayerID = null;
        this.mockBoard = null;
        this.mockPlayers = new ArrayList<>();
        this.mockCommonGoal = null;
        this.chat = null;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public UUID getUuid() {
        return uuid;
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
    public void addMockPlayer(MockPlayer mockPlayer){
        this.mockPlayers.add(mockPlayer);
    }
    public List<MockPlayer> getMockPlayers() {
        return mockPlayers;
    }
    public String getLocalPlayerID() {
        return localPlayerID;
    }
    public void setLocalPlayerID(String playerID) {
        this.localPlayerID = playerID;
    }

    public List<String> getLobby() {
        return lobby;
    }

    public void setLobby(List<String> lobby) {
        this.lobby = lobby;
    }

    public void setMockModel(GameModel model){

    }
    public Stack<ChatMessage> getChat(){
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
