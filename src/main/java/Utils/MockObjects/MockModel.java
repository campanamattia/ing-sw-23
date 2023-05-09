package Utils.MockObjects;

import Server.Model.ChatMessage;
import Server.Model.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class MockModel {
    private UUID uuid;
    private String playerID;
    private MockBoard mockBoard;
    private List<MockPlayer> mockPlayers;
    private MockCommonGoal mockCommonGoal;
    private final Stack<ChatMessage> chat;

    public MockModel() {
        this.uuid = null;
        this.playerID = null;
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
    public void setMockCommonGoal(MockCommonGoal mockCommonGoal) {
        this.mockCommonGoal = mockCommonGoal;
    }
    public MockCommonGoal getMockCommonGoal() {
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
    public String getPlayerID() {
        return playerID;
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
    public void setMockModel(GameModel model){

    }
    public Stack<ChatMessage> getChat(){
        return chat;
    }
    public void addMessage(ChatMessage message) {
        chat.add(message);
    }

}
