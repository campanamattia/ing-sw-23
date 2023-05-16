package Messages.Server.Listener;

import Client.View.View;
import Messages.ServerMessage;
import Utils.MockObjects.MockCommonGoal;

public class CommonGoalUpdate extends ServerMessage {
    private MockCommonGoal mockCommonGoal;

    public CommonGoalUpdate(MockCommonGoal mockCommonGoal) {
        this.mockCommonGoal = mockCommonGoal;
    }

    @Override
    public void execute(View view) {
        view.updateCommonGoal(this.mockCommonGoal);
    }
}
