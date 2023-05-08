package Utils;

import Utils.MockObjects.MockBoard;
import Utils.MockObjects.MockCommonGoal;
import Utils.MockObjects.MockPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockModel {
    private UUID uuid;
    private MockBoard mockBoard;
    private List<MockPlayer> mockPlayers;
    private MockCommonGoal mockCommonGoal;

    public MockModel() {
        this.uuid = null;
        this.mockBoard = null;
        this.mockPlayers = new ArrayList<>();
        this.mockCommonGoal = null;
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
}
