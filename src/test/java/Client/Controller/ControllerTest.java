package Client.Controller;

import Exception.Board.NoValidMoveException;
import Server.Model.Coordinates;
import org.junit.jupiter.api.Test;
import Exception.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller = new Controller();

    @Test
    void inputReader() {
        assertThrows(InvalidInputException.class, () -> {
            this.controller.inputReader();
        });
    }
}