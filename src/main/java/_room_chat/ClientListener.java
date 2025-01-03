package _room_chat;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientListener {
    private final InputStream is;
    public ClientListener(Socket socket) {
        try {
            this.is = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void receiveMessage() {
        try {
            var bytes = new byte[1024];
            var byteRead = 0;
            while ((byteRead = this.is.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, byteRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
