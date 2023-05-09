package Client.Network;

import Messages.ClientMessage;
import Messages.ServerMessage;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocket extends Network{
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientSocket() throws IOException {
        this.socket = new Socket(this.ipAddress,this.port);
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
    }

    @Override
    public void sendMessage(ClientMessage clientMessage) throws IOException {
        ServerMessage serverMessage;
        JsonObject jsonObjectClient = new JsonObject();
        // serialize json
        // .......
        outputStream.write("jsonObject-to-set".getBytes());

        // read from the server
        // inputStream.read(buffer);

        // create serverMessage for json
        // .....
    }
}