package Client;

import Exception.*;
import Utils.Coordinates;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ClientAppTest {

    ClientApp clientApp = new ClientApp();

    @Test
    void main() {
        assertDoesNotThrow(() -> {
            String input = "SOCKET\n1234\n127.0.0.1\n";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            String[] args = new String[]{"Program","CLI"};
            clientApp.main(args);
        });
    }
}